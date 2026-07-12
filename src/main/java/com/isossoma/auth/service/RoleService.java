package com.isossoma.auth.service;

import com.isossoma.auth.dto.filters.RolePageableFilters;
import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.role.RoleSimpleResponse;
import com.isossoma.auth.dto.response.role.RoleDetailResponse;
import org.springframework.data.domain.Page;

public interface RoleService {
    RoleSimpleResponse createRole(CreateRoleRequest request);
    RoleDetailResponse findById(Long roleId);
    RoleSimpleResponse updateRole(Long roleId, UpdateRoleRequest request);
    Page<RoleSimpleResponse> findAll(RolePageableFilters filters);
    void deleteRole(Long roleId);
    void reactivateRole(Long roleId);
}