package com.isossoma.auth.dto.request;

import java.util.Set;

public record CreateUserRequest(
        String username,
        String email,
        String firstname,
        String lastname,
        String password,
        Set<Long> roleIds
) {}