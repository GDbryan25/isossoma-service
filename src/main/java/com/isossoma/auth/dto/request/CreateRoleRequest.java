package com.isossoma.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateRoleRequest(
        @NotBlank
        String name,
        String description,
        @NotEmpty
        Set<Long> permissionIds
) {}