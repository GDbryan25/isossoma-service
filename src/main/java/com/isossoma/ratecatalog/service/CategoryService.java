package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllCategoriesByServiceType(Long typeId);
}