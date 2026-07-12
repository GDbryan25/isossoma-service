package com.isossoma.ratecatalog.dto.response.item;

import com.isossoma.ratecatalog.enums.ParameterType;
import com.isossoma.shared.model.enums.RecordStatus;
import lombok.Builder;

@Builder
public record ItemResponse(
        Long id,
        String description,
        ParameterType parameterType,
        String note,
        RecordStatus status,
        String categoryDescription
) {}
