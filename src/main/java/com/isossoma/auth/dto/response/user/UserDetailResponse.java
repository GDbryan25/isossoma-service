package com.isossoma.auth.dto.response.user;

import com.isossoma.auth.dto.response.role.RoleDetailResponse;
import com.isossoma.shared.model.enums.RecordStatus;
import java.util.Set;

public record UserDetailResponse(
        Long id,
        String username,
        String email,
        String firstname,
        String lastname,
        RecordStatus status,
        Set<RoleDetailResponse> roles
) {}