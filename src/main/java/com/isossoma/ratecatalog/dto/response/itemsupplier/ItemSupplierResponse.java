package com.isossoma.ratecatalog.dto.response.itemsupplier;

import com.isossoma.shared.model.enums.RecordStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemSupplierResponse(
        Long id,
        BigDecimal price,
        String methodology,
        String accreditation,
        String location,
        RecordStatus status,
        String supplierDescription
) {}