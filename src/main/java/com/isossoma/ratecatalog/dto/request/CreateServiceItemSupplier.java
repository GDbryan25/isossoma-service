package com.isossoma.ratecatalog.dto.request;

import java.math.BigDecimal;

public record CreateServiceItemSupplier(
        String code,
        String description,
        String methodology,
        String accreditation,
        BigDecimal price
) {}