package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.response.GetServiceCategory;
import com.isossoma.ratecatalog.model.*;
import com.isossoma.ratecatalog.repository.ServiceCategoryRepository;
import com.isossoma.ratecatalog.service.CategoryService;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ServiceCategoryRepository repository;

    @Override
    public Page<GetServiceCategory> getAllServiceCategories(Pageable pageable) {
        return repository.findAllProjected(pageable);
    }

    @Override
    public GetServiceCategory getById(Long id) {
        return repository.findCategoryProjectedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory not found"));
    }

    @Transactional
    @Override
    public void softDeleteServiceCategory(Long categoryServiceId) {
        ServiceCategory category = repository.findById(categoryServiceId)
                .orElseThrow(() -> new RuntimeException("ServiceCategory not found"));

        category.setStatus(RecordStatus.INACTIVE);

        for (ServiceItem item : category.getItems()) {
            item.setStatus(RecordStatus.INACTIVE);

            for (ServiceItemSupplier supplier : item.getSuppliers()) {
                supplier.setStatus(RecordStatus.INACTIVE);
            }
        }

        repository.save(category);
    }

    @Override
    public List<GetServiceCategory> getCategoriesByServiceType(Long typeId) {
        return repository.findAllByServiceTypeId(typeId);
    }
}