package com.isossoma.auth.dto.response.access;

import java.util.Set;

public record SubmenuAccessResponse(
        String key,
        String route,
        Set<String> actions
) {}
