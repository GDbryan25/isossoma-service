package com.isossoma.ratecatalog.dto.filters;

import com.isossoma.shared.model.enums.RecordStatus;

public record SupplierFilter(
        Integer page,
        Integer size,
        String name,
        RecordStatus status
) {
    public SupplierFilter {
        name = (name != null && name.isBlank()) ? null : name;
        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size < 1) ? 10 : size;
    }
}