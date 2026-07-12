package com.isossoma.auth.dto.response.access;

public record PermissionAccessResponse(
        Long id,
        String authority,
        String description,
        String menuKey,
        String submenuKey,
        String actionKey,
        String route
) {}
