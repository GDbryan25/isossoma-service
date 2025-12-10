package com.isossoma.ratecatalog.dto.response;

import com.isossoma.ratecatalog.model.ParameterType;

public record GetServiceItem(
        Long id,
        String code,
        String description,
        ParameterType parameterType,
        String category
) {}