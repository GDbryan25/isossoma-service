package com.isossoma.auth.service;

import com.isossoma.auth.dto.request.AuthenticationRequest;
import com.isossoma.auth.dto.request.RegisterRequest;
import com.isossoma.auth.dto.response.AuthenticationResponse;
import com.isossoma.auth.exception.UserAlreadyExistsException;
import com.isossoma.auth.models.Role;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.repository.UserRepository;
import com.isossoma.auth.security.JwtService;
import com.isossoma.auth.security.UserPrincipal;
import com.isossoma.auth.models.User;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .isActive(true)
                .isLocked(false)
                .build();

        userRepository.save(user);

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        var jwtToken = jwtService.generateToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsernameWithRolesAndPermissions(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        var jwtToken = jwtService.generateToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return new AuthenticationResponse(jwtToken, refreshToken);
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

                return new AuthenticationResponse(accessToken, refreshToken);
            }
        }

        throw new RuntimeException("Invalid refresh token");
    }
}