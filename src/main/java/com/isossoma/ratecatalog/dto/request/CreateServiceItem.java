package com.isossoma.ratecatalog.dto.request;

import java.util.List;

public record CreateServiceItem(
        String code,
        String description,
        String parameterType,
        Long serviceCategoryId,
        List<CreateServiceItemSupplier> suppliers
) {}