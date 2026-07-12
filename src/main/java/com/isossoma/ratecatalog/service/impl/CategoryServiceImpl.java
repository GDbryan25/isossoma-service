package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.response.CategoryResponse;
import com.isossoma.ratecatalog.mapper.ServiceCategoryMapper;
import com.isossoma.ratecatalog.repository.CategoryRepository;
import com.isossoma.ratecatalog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final ServiceCategoryMapper mapper;

    @Override
    public List<CategoryResponse> findAllCategoriesByServiceType(Long typeId) {
        return repository.findByOptionalServiceTypeId(typeId).stream()
                .map(mapper::toCategoryResponse)
                .toList();
    }
}