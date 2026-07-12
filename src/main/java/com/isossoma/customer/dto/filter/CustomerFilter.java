package com.isossoma.customer.dto.filter;

import com.isossoma.shared.model.enums.RecordStatus;

public record CustomerFilter(
        String name,
        RecordStatus status,
        Integer page,
        Integer size
) {
    public CustomerFilter {
        name = (name != null && name.isBlank()) ? null : name;
        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size < 1) ? 10 : size;
    }
}