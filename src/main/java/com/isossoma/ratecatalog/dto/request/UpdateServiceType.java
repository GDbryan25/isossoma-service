package com.isossoma.ratecatalog.dto.request;

public record UpdateServiceType(
        Long serviceId,
        String code,
        String description,
        UpdateServiceCategory category
) {}