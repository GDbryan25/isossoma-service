package com.isossoma.ratecatalog.dto.filters;

import com.isossoma.shared.model.enums.RecordStatus;

public record ItemSupplierPageableFilter(
        Long itemId,
        RecordStatus status,
        Integer page,
        Integer size,
        String name
) {
    public ItemSupplierPageableFilter {
        name = (name == null || name.isBlank())
                ? null
                : name.trim();

        page = (page == null || page < 0)
                ? 0
                : page;

        size = (size == null || size < 1)
                ? 10
                : size;
    }
}