package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.PermissionResponse;
import com.isossoma.auth.dto.response.RoleSimpleResponse;
import com.isossoma.auth.dto.response.RoleWithPermissionsResponse;
import com.isossoma.auth.models.Permission;
import com.isossoma.auth.models.Role;
import com.isossoma.auth.repository.PermissionRepository;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.service.RoleService;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.permissionIds()));

        if (permissions.isEmpty()) {
            throw new IllegalArgumentException("No valid permissions were found");
        }

        Role role = Role.builder()
                .name(request.name())
                .description(request.description())
                .permissions(permissions)
                .build();

        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleWithPermissionsResponse getRoleWithPermissions(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));

        Set<PermissionResponse> permissions = role.getPermissions()
                .stream()
                .map(p -> new PermissionResponse(
                        p.getId(),
                        p.getCode(),
                        p.getDescription(),
                        p.getIsActive()
                ))
                .collect(Collectors.toSet());

        return new RoleWithPermissionsResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getIsActive(),
                permissions
        );
    }

    @Override
    @Transactional
    public void removePermissionFromRole(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NoSuchElementException("Permission not found"));

        boolean removed = role.getPermissions().remove(permission);

        if (!removed) {
            throw new IllegalArgumentException("The role does not contain this permission");
        }

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void updateRole(Long roleId, UpdateRoleRequest request) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));

        if (!role.getName().equalsIgnoreCase(request.name()) &&
                roleRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        role.setName(request.name());
        role.setDescription(request.description());

        Set<Permission> permissionsToAdd = new HashSet<>(permissionRepository.findAllById(request.permissionIds()));

        role.getPermissions().addAll(permissionsToAdd);

        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleSimpleResponse> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(role -> new RoleSimpleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getIsActive()
                ));
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        role.getPermissions().clear();

        role.setIsActive(false);

        roleRepository.save(role);
    }
}