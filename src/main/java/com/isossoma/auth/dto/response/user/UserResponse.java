package com.isossoma.auth.dto.response.user;

import com.isossoma.shared.model.enums.RecordStatus;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstname,
        String lastname,
        RecordStatus status
) {}