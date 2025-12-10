package com.isossoma.auth.service;

import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.RoleSimpleResponse;
import com.isossoma.auth.dto.response.RoleWithPermissionsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    void createRole(CreateRoleRequest request);
    RoleWithPermissionsResponse getRoleWithPermissions(Long roleId);
    void removePermissionFromRole(Long roleId, Long permissionId);
    void updateRole(Long roleId, UpdateRoleRequest request);
    Page<RoleSimpleResponse> getRoles(Pageable pageable);
    void deleteRole(Long roleId);
}