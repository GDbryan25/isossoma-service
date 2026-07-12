package com.isossoma.ratecatalog.dto.response;

import com.isossoma.shared.model.enums.RecordStatus;
import lombok.Builder;

@Builder
public record SupplierResponse(
        Long id,
        String name,
        String note,
        RecordStatus status
) {}