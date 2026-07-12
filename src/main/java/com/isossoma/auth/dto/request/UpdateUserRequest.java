package com.isossoma.auth.dto.request;

import java.util.Set;

public record UpdateUserRequest(
        String username,
        String email,
        String firstname,
        String lastname,
        Set<Long> roleIds
) {}