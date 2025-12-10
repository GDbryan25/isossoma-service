package com.isossoma.ratecatalog.dto.request;

import java.math.BigDecimal;

public record CreateSupplier(
        String code,
        String description,
        String methodology,
        String accreditation,
        BigDecimal price,
        Long serviceItemId
) {}