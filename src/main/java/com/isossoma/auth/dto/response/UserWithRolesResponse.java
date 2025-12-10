package com.isossoma.auth.dto.response;

import java.util.Set;

public record UserWithRolesResponse(
        Long id,
        String username,
        String email,
        String firstname,
        String lastname,
        Boolean isActive,
        Boolean isLocked,
        Set<RoleSimpleResponse> roles
) {}