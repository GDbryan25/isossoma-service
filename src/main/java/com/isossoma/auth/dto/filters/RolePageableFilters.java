package com.isossoma.auth.dto.filters;

import com.isossoma.shared.model.enums.RecordStatus;

public record RolePageableFilters(
        RecordStatus status,
        String name,
        Integer page,
        Integer size
) {}