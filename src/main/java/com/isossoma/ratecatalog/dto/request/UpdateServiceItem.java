package com.isossoma.ratecatalog.dto.request;

import com.isossoma.ratecatalog.enums.ParameterType;

public record UpdateServiceItem(
        String description,
        ParameterType parameterType,
        String note,
        Long serviceCategoryId
) {}