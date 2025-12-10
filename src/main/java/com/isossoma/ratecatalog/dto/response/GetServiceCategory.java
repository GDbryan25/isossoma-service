package com.isossoma.ratecatalog.dto.response;

import com.isossoma.ratecatalog.model.RecordStatus;

public record GetServiceCategory(
        Long categoryId,
        String code,
        String description,
        RecordStatus status,
        String serviceType,
        Long serviceTypeId
) {}