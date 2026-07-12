package com.isossoma.ratecatalog.dto.filters;

import com.isossoma.ratecatalog.enums.ParameterType;
import com.isossoma.shared.model.enums.RecordStatus;

public record ServiceItemFilter(
        String description,
        ParameterType parameterType,
        RecordStatus status,
        Long categoryId,
        Long supplierId
) {}
