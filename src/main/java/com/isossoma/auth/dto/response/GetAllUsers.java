package com.isossoma.auth.dto.response;

public record GetAllUsers(
        Long id,
        String username,
        String email,
        boolean isActive,
        boolean isLocked
) {}