package com.isossoma.auth.controller;

import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.service.UserService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseBuilder.created("Usuario creado correctamente", null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id) {
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, userService.listUsers(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        userService.updateUser(id, request);

        return ResponseBuilder.ok(
                "Registro actualizado correctamente",
                null
        );
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponse> removeRoleFromUser(
            @PathVariable Long userId,
            @PathVariable Long roleId
    ) {
        userService.removeRoleFromUser(userId, roleId);

        return ResponseBuilder.ok(
                "Registro actualizado correctamente",
                null
        );
    }
}