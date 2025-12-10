package com.isossoma.ratecatalog.dto.request;

public record UpdateServiceItem(
        Long id,
        String code,
        String description,
        String parameterType,
        Long serviceCategoryId
) {}