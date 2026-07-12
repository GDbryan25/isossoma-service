package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.filters.UserPageableFilters;
import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.auth.dto.response.role.RoleDetailResponse;
import com.isossoma.auth.dto.response.user.UserDetailResponse;
import com.isossoma.auth.dto.response.user.UserResponse;
import com.isossoma.auth.exception.UserAlreadyExistsException;
import com.isossoma.auth.mapper.UserMapper;
import com.isossoma.auth.models.entities.Role;
import com.isossoma.auth.models.entities.User;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.repository.UserRepository;
import com.isossoma.auth.service.UserService;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsernameAndDeletedAtIsNull(request.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (request.roleIds() == null || request.roleIds().isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }

        Set<Role> roles = new HashSet<>(
                roleRepository.findAllById(request.roleIds())
        );

        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("No valid roles found for given ids");
        }

        User user = new User(mapper.toUserInformation(request), roles);

        return mapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResponse findById(Long id) {
        User user = userRepository.findByIdWithRolesAndPermissions(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<RoleDetailResponse> roles = user.getRoles()
                .stream()
                .map(role -> new RoleDetailResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getStatus(),
                        role.getPermissions()
                                .stream()
                                .map(permission -> new PermissionResponse(
                                        permission.getId(),
                                        permission.getCode(),
                                        permission.getDescription(),
                                        permission.getMenuKey(),
                                        permission.getSubmenuKey(),
                                        permission.getActionKey(),
                                        permission.getRoute(),
                                        permission.getStatus()
                                ))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toSet());

        return new UserDetailResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getStatus(),
                roles
        );
    }

    @Override
    public Page<UserResponse> listUsers(UserPageableFilters filter) {
        Pageable pageable = PageRequest.of(filter.page(), filter.size(), Sort.by("id").descending());

        return userRepository.findUsers(
                filter.status(),
                filter.firstname(),
                filter.lastname(),
                pageable
        );
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.deactivate();
    }

    @Override
    @Transactional
    public void reactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.reactivate();
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userRepository.existsByUsernameAndDeletedAtIsNullAndIdNot(request.username(), userId)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmailAndDeletedAtIsNullAndIdNot(request.email(), userId)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (request.roleIds() == null || request.roleIds().isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.roleIds()));

        if (roles.size() != request.roleIds().size()) {
            throw new ResourceNotFoundException("One or more roles do not exist");
        }

        user.update(
                request.username(),
                request.email(),
                request.firstname(),
                request.lastname(),
                roles
        );

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getStatus()
        );
    }
}