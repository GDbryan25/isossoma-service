package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.request.AuthenticationRequest;
import com.isossoma.auth.dto.request.RegisterRequest;
import com.isossoma.auth.dto.response.AuthenticationResponse;
import com.isossoma.auth.dto.response.access.AccessProfileResponse;
import com.isossoma.auth.dto.response.access.MenuAccessResponse;
import com.isossoma.auth.dto.response.access.PermissionAccessResponse;
import com.isossoma.auth.dto.response.access.SubmenuAccessResponse;
import com.isossoma.auth.exception.UserAlreadyExistsException;
import com.isossoma.auth.models.entities.Permission;
import com.isossoma.auth.models.entities.Role;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.repository.UserRepository;
import com.isossoma.auth.security.JwtService;
import com.isossoma.auth.security.PermissionAuthorityResolver;
import com.isossoma.auth.security.UserPrincipal;
import com.isossoma.auth.models.entities.User;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsernameAndDeletedAtIsNull(request.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        Role defaultRole = roleRepository.findByNameAndDeletedAtIsNull("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role USER not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        var user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()), roles);

        userRepository.save(user);

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        var jwtToken = jwtService.generateToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return new AuthenticationResponse(jwtToken, refreshToken, buildAccessProfile(user));
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsernameWithRolesAndPermissions(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.username()));

        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!matches) {
            throw new BadCredentialsException("Password does not match!");
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (Exception e) {
            throw e;
        }

        var jwtToken = jwtService.generateToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return new AuthenticationResponse(jwtToken, refreshToken, buildAccessProfile(user));
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse refreshToken(String refreshToken) {
        final String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            var user = userRepository.findByUsernameWithRolesAndPermissions(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            UserPrincipal userPrincipal = UserPrincipal.create(user);

            if (jwtService.isTokenValid(refreshToken, userPrincipal)) {
                var accessToken = jwtService.generateToken(userPrincipal);

                return new AuthenticationResponse(accessToken, refreshToken, buildAccessProfile(user));
            }
        }

        throw new RuntimeException("Invalid refresh token");
    }

    @Transactional(readOnly = true)
    public AccessProfileResponse getAccessProfile(String username) {
        User user = userRepository.findByUsernameWithRolesAndPermissions(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        return buildAccessProfile(user);
    }

    private AccessProfileResponse buildAccessProfile(User user) {
        Set<String> roles = new TreeSet<>();
        Set<String> permissions = new TreeSet<>();
        Map<String, PermissionAccessResponse> permissionDetailsByAuthority = new LinkedHashMap<>();
        Map<String, MenuNode> menuByKey = new LinkedHashMap<>();

        for (Role role : user.getRoles()) {
            roles.add(role.getName());

            for (Permission permission : role.getPermissions()) {
                String authority = PermissionAuthorityResolver.resolvePermissionAuthority(permission);
                PermissionAuthorityResolver.PermissionScope scope = PermissionAuthorityResolver.resolvePermissionScope(permission);

                permissions.add(authority);
                permissionDetailsByAuthority.putIfAbsent(authority, new PermissionAccessResponse(
                        permission.getId(),
                        authority,
                        permission.getDescription(),
                        scope.menuKey(),
                        scope.submenuKey(),
                        scope.actionKey(),
                        permission.getRoute()
                ));

                if (!StringUtils.hasText(scope.menuKey())) {
                    continue;
                }

                MenuNode menuNode = menuByKey.computeIfAbsent(scope.menuKey(), key -> new MenuNode(key, null));
                if (!StringUtils.hasText(menuNode.route) && !StringUtils.hasText(scope.submenuKey())) {
                    menuNode.route = permission.getRoute();
                }

                if (StringUtils.hasText(scope.submenuKey())) {
                    SubmenuNode submenuNode = menuNode.submenus.computeIfAbsent(
                            scope.submenuKey(),
                            key -> new SubmenuNode(key, permission.getRoute())
                    );

                    if (!StringUtils.hasText(submenuNode.route)) {
                        submenuNode.route = permission.getRoute();
                    }

                    if (StringUtils.hasText(scope.actionKey())) {
                        submenuNode.actions.add(scope.actionKey());
                    }
                }
            }
        }

        Set<MenuAccessResponse> menus = new TreeSet<>(Comparator.comparing(MenuAccessResponse::key));

        for (MenuNode menuNode : menuByKey.values()) {
            Set<SubmenuAccessResponse> submenus = new TreeSet<>(Comparator.comparing(SubmenuAccessResponse::key));

            for (SubmenuNode submenuNode : menuNode.submenus.values()) {
                submenus.add(new SubmenuAccessResponse(
                        submenuNode.key,
                        submenuNode.route,
                        new TreeSet<>(submenuNode.actions)
                ));
            }

            menus.add(new MenuAccessResponse(menuNode.key, menuNode.route, submenus));
        }

        List<PermissionAccessResponse> permissionDetails = new ArrayList<>(permissionDetailsByAuthority.values());
        permissionDetails.sort(Comparator.comparing(PermissionAccessResponse::authority));

        return new AccessProfileResponse(
                user.getId(),
                user.getUsername(),
                roles,
                permissions,
                menus,
                permissionDetails
        );
    }

    private static class MenuNode {
        private final String key;
        private String route;
        private final Map<String, SubmenuNode> submenus = new LinkedHashMap<>();

        private MenuNode(String key, String route) {
            this.key = key;
            this.route = route;
        }
    }

    private static class SubmenuNode {
        private final String key;
        private String route;
        private final Set<String> actions = new TreeSet<>();

        private SubmenuNode(String key, String route) {
            this.key = key;
            this.route = route;
        }
    }
}