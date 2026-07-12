package com.isossoma.ratecatalog.dto.response.itemsupplier;

import com.isossoma.shared.model.enums.RecordStatus;
import java.math.BigDecimal;

public record ItemSupplierDetailResponse(
        Long id,
        BigDecimal price,
        String methodology,
        String accreditation,
        String location,
        RecordStatus status,
        Long itemId,
        Long supplierId,
        String supplierDescription
) {}