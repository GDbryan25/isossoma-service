package com.isossoma.ratecatalog.dto.request;

import java.math.BigDecimal;

public record UpdateItemSupplier(
        Long supplierId,
        BigDecimal price,
        String methodology,
        String accreditation,
        String location
) {}