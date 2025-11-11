package com.isossoma.shared.dto;

import java.time.OffsetDateTime;

public record ApiResponse(
        boolean success,
        String message,
        Object data,
        OffsetDateTime timestamp
) {}