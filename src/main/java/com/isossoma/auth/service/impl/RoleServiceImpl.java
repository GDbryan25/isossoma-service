package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.filters.RolePageableFilters;
import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.auth.dto.response.role.RoleSimpleResponse;
import com.isossoma.auth.dto.response.role.RoleDetailResponse;
import com.isossoma.auth.models.entities.Permission;
import com.isossoma.auth.models.entities.Role;
import com.isossoma.auth.repository.PermissionRepository;
import com.isossoma.auth.repository.RoleRepository;
import com.isossoma.auth.service.RoleService;
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
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleSimpleResponse createRole(CreateRoleRequest request) {
        if(roleRepository.existsByName(request.name())){
            throw new RuntimeException("Role already exists.");
        }

        Set<Permission> permissions = getPermissions(request.permissionIds());

        Role role = new Role(request.name(), request.description(), permissions);

        Role saved = roleRepository.save(role);

        return new RoleSimpleResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getStatus()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDetailResponse findById(Long roleId) {
        Role role = roleRepository.findWithPermissionsById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        Set<PermissionResponse> permissions = role.getPermissions()
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
                .collect(Collectors.toSet());

        return new RoleDetailResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getStatus(),
                permissions
        );
    }

    @Override
    @Transactional
    public RoleSimpleResponse updateRole(Long roleId, UpdateRoleRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        if(roleRepository.existsByNameAndIdNot(request.name(), roleId)){
            throw new RuntimeException("Role name already exists.");
        }

        Set<Permission> permissions = getPermissions(request.permissionIds());

        role.update(request.name(), request.description(), permissions);

        return new RoleSimpleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getStatus()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleSimpleResponse> findAll(RolePageableFilters filters) {
        int page = filters.page() != null ? filters.page() : 0;
        int size = filters.size() != null ? filters.size() : 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return roleRepository.findAll(filters.status(), filters.name(), pageable)
                .map(role -> new RoleSimpleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getStatus()
                ));
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        role.deactivate();
    }

    @Override
    @Transactional
    public void reactivateRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        role.reactivate();
    }

    private Set<Permission> getPermissions(Set<Long> permissionIds) {
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(permissionIds));

        if (permissions.size() != permissionIds.size()) {
            throw new RuntimeException("One or more permissions do not exist.");
        }

        return permissions;
    }
}