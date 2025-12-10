package com.isossoma.ratecatalog.dto.request;

import java.util.List;

public record CreateServiceType(
        Long serviceId,
        String code,
        String description,
        List<CreateServiceCategory> categories
) {}