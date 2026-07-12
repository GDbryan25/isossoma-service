package com.isossoma.auth.models.valueobjects;

import lombok.Builder;
import java.util.Set;

@Builder
public record UserInformation(
        String username,
        String email,
        String firstName,
        String lastName,
        String password
) {}