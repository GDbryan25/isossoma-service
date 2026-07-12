package com.isossoma.auth.controller;

import com.isossoma.auth.dto.filters.RolePageableFilters;
import com.isossoma.auth.dto.request.CreateRoleRequest;
import com.isossoma.auth.dto.request.UpdateRoleRequest;
import com.isossoma.auth.dto.response.role.RoleSimpleResponse;
import com.isossoma.auth.dto.response.role.RoleDetailResponse;
import com.isossoma.auth.service.RoleService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateRoleRequest request) {
        RoleSimpleResponse role = roleService.createRole(request);

        return ResponseBuilder.created(
                "Rol creado correctamente",
                role
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        RoleSimpleResponse role = roleService.updateRole(id, request);

        return ResponseBuilder.ok(
                "Rol actualizado correctamente",
                role
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        RoleDetailResponse role = roleService.findById(id);

        return ResponseBuilder.ok(
                SuccessMessages.QUERY_SUCCESSFULLY,
                role
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(@ModelAttribute RolePageableFilters filter) {
        Page<RoleSimpleResponse> roles = roleService.findAll(filter);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivateRole(@PathVariable Long id) {
        roleService.reactivateRole(id);

        return ResponseBuilder.ok(
                "Registro reactivado correctamente",
                null
        );
    }
}