package com.isossoma.ratecatalog.dto.request;

public record UpdateServiceCategory(
        Long id,
        String code,
        String description
) {}