package com.isossoma.ratecatalog.dto.response;

public record GetItemDetails(
        Long id,
        String code,
        String description,
        String parameterType,
        String serviceCategoryId,
        String serviceTypeId
) {}