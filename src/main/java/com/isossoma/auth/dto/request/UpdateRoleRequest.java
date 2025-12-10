package com.isossoma.auth.dto.request;

import java.util.Set;

public record UpdateRoleRequest(
        String name,
        String description,
        Set<Long> permissionIds
) {}