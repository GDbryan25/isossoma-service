package com.isossoma.ratecatalog.mapper;

import com.isossoma.ratecatalog.dto.response.CategoryResponse;
import com.isossoma.ratecatalog.model.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryMapper {
    public CategoryResponse toCategoryResponse(ServiceCategory category) {
        return new CategoryResponse(category.getId(), category.getDescription());
    }
}
