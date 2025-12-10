package com.isossoma.ratecatalog.dto.response;

public record GetServiceItem(
        Long id,
        String code,
        String description,
        String parameterType,
        String category
) {}