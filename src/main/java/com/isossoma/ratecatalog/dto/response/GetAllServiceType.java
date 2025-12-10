package com.isossoma.ratecatalog.dto.response;

public record GetAllServiceType(
        Long id,
        String code,
        String description
) {}