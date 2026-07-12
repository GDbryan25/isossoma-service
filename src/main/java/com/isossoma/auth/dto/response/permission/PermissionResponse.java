package com.isossoma.auth.dto.response.permission;

import com.isossoma.shared.model.enums.RecordStatus;

public record PermissionResponse(
        Long id,
        String code,
        String description,
        String menuKey,
        String submenuKey,
        String actionKey,
        String route,
        RecordStatus status
) {}