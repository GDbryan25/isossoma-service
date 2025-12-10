package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.dto.response.GetAllUsers;
import com.isossoma.auth.dto.response.RoleSimpleResponse;
import com.isossoma.auth.dto.response.UserWithRolesResponse;
import com.isossoma.auth.models.Role;
import com.isossoma.auth.models.User;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.repository.UserRepository;
import com.isossoma.auth.service.UserService;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.roleIds()));

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("No valid roles provided");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserWithRolesResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserWithRolesResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getIsActive(),
                user.getIsLocked(),
                user.getRoles()
                        .stream()
                        .map(role -> new RoleSimpleResponse(
                                role.getId(),
                                role.getName(),
                                role.getDescription(),
                                role.getIsActive()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Page<GetAllUsers> listUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.getRoles().clear();

        user.setIsActive(true);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setIsActive(request.isActive());
        user.setIsLocked(request.isLocked());

        Set<Role> rolesToAdd = roleRepository.findAllById(request.roleIds())
                .stream()
                .collect(Collectors.toSet());

        user.getRoles().addAll(rolesToAdd);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().remove(role);

        userRepository.save(user);
    }
}