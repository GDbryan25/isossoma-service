package com.isossoma.auth.controller;

import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.RoleWithPermissionsResponse;
import com.isossoma.auth.service.RoleService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateRoleRequest request) {
        roleService.createRole(request);
        return ResponseBuilder.created(
                "Rol creado correctamente",
                null
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        roleService.updateRole(id, request);
        return ResponseBuilder.ok(
                "Rol actualizado correctamente",
                null
        );
    }

    @GetMapping("/{id}/with-permissions")
    public ResponseEntity<ApiResponse> getRoleWithPermissions(@PathVariable Long id) {
        RoleWithPermissionsResponse role = roleService.getRoleWithPermissions(id);
        return ResponseBuilder.ok(
                SuccessMessages.QUERY_SUCCESSFULLY,
                role
        );
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<ApiResponse> removePermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId
    ) {
        roleService.removePermissionFromRole(roleId, permissionId);
        return ResponseBuilder.ok(
                SuccessMessages.QUERY_SUCCESSFULLY,
                null
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(Pageable pageable) {
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, roleService.getRoles(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }
}