package com.isossoma.auth.controller;

import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.auth.service.PermissionService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(@RequestParam(required = false) String name) {
        List<PermissionResponse> permissions = permissionService.findAll(name);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, permissions);
    }
}