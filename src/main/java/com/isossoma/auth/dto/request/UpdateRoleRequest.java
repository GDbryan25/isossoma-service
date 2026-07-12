package com.isossoma.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record UpdateRoleRequest(
        @NotBlank
        String name,
        String description,
        @NotEmpty
        Set<Long> permissionIds
) {}