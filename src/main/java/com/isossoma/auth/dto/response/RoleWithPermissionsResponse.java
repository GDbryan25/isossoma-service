package com.isossoma.auth.dto.response;

import java.util.Set;

public record RoleWithPermissionsResponse(
        Long id,
        String name,
        String description,
        Boolean isActive,
        Set<PermissionResponse> permissions
) {}