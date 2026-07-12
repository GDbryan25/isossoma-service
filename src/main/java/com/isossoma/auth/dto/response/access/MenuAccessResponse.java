package com.isossoma.auth.dto.response.access;

import java.util.Set;

public record MenuAccessResponse(
        String key,
        String route,
        Set<SubmenuAccessResponse> submenus
) {}
