package com.isossoma.auth.dto.response;

public record PermissionSimpleResponse(
        Long id,
        String code,
        String description,
        Boolean isActive
) {}