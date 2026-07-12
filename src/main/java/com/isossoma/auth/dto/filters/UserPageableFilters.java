package com.isossoma.auth.dto.filters;

import com.isossoma.shared.model.enums.RecordStatus;

public record UserPageableFilters(
        RecordStatus status,
        String firstname,
        String lastname,
        Integer size,
        Integer page
) {
    public UserPageableFilters {
        firstname = (firstname != null && firstname.isBlank()) ? null : firstname;
        lastname = (lastname != null && lastname.isBlank()) ? null : lastname;
        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size < 1) ? 10 : size;
    }
}