package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.response.GetServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<GetServiceCategory> getAllServiceCategories(Pageable pageable);
    GetServiceCategory getById(Long id);
    void softDeleteServiceCategory(Long categoryServiceId);
    List<GetServiceCategory> getCategoriesByServiceType(Long typeId);
}