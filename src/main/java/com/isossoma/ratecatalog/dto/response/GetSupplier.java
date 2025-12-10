package com.isossoma.ratecatalog.dto.response;

import java.math.BigDecimal;

public record GetSupplier(
        Long id,
        String code,
        String description,
        String methodology,
        String accreditation,
        BigDecimal price,
        Long serviceItemId
) {}