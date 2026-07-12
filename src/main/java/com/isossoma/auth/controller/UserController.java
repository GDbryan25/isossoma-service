package com.isossoma.auth.controller;

import com.isossoma.auth.dto.filters.UserPageableFilters;
import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.dto.response.user.UserDetailResponse;
import com.isossoma.auth.dto.response.user.UserResponse;
import com.isossoma.auth.service.UserService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateUserRequest request) {
        UserResponse user = service.createUser(request);

        return ResponseBuilder.created("Usuario creado correctamente", user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        UserDetailResponse userDetails = service.findById(id);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, userDetails);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listUsers(@ModelAttribute UserPageableFilters filter) {
        Page<UserResponse> users = service.listUsers(filter);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivateUser(@PathVariable Long id) {
        service.reactivateUser(id);

        return ResponseBuilder.ok(
                "Registro reactivado correctamente",
                null
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UserResponse user = service.updateUser(id, request);

        return ResponseBuilder.ok(
                "Registro actualizado correctamente",
                user
        );
    }
}