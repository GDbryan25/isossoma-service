package com.isossoma.auth.dto.response;

public record PermissionResponse(
        Long id,
        String code,
        String description,
        Boolean isActive
) {}