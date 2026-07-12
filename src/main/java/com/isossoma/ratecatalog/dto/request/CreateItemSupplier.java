package com.isossoma.ratecatalog.dto.request;

import java.math.BigDecimal;

public record CreateItemSupplier(
        Long itemId,
        Long supplierId,
        BigDecimal price,
        String methodology,
        String accreditation,
        String location
) {}