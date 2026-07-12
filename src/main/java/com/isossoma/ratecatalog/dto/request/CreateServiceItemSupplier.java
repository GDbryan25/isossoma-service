package com.isossoma.ratecatalog.dto.request;

import java.math.BigDecimal;

public record CreateServiceItemSupplier(
        Long supplierId,
        String methodology,
        String accreditation,
        BigDecimal price,
        String location
) {}