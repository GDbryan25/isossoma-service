package com.isossoma.auth.dto.response.role;

import com.isossoma.shared.model.enums.RecordStatus;

public record RoleSimpleResponse(
        Long id,
        String name,
        String description,
        RecordStatus status
) {}