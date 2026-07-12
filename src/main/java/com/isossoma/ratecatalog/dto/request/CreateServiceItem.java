package com.isossoma.ratecatalog.dto.request;

import java.util.List;

public record CreateServiceItem(
        String description,
        String parameterType,
        String note,
        Long serviceCategoryId,
        List<CreateServiceItemSupplier> suppliers
) {}