package com.isossoma.auth.dto.response.role;

import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.shared.model.enums.RecordStatus;
import java.util.Set;

public record RoleDetailResponse(
        Long id,
        String name,
        String description,
        RecordStatus status,
        Set<PermissionResponse> permissions
) {}