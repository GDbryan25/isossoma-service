package com.isossoma.ratecatalog.dto.response;

import com.isossoma.ratecatalog.model.ParameterType;

public record GetItemDetails(
        Long id,
        String code,
        String description,
        ParameterType parameterType,
        Long serviceCategoryId,
        Long serviceTypeId
) {}