package com.isossoma.auth.dto.response;

public record RoleSimpleResponse(
        Long id,
        String name,
        String description,
        Boolean isActive
) {}