package com.isossoma.auth.dto.response.access;

import java.util.List;
import java.util.Set;

public record AccessProfileResponse(
        Long userId,
        String username,
        Set<String> roles,
        Set<String> permissions,
        Set<MenuAccessResponse> menus,
        List<PermissionAccessResponse> permissionDetails
) {}
