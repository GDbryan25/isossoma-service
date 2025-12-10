package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.GetItemDetails;
import com.isossoma.ratecatalog.dto.response.GetServiceItem;
import com.isossoma.ratecatalog.model.*;
import com.isossoma.ratecatalog.repository.ServiceCategoryRepository;
import com.isossoma.ratecatalog.repository.ServiceItemRepository;
import com.isossoma.ratecatalog.service.ItemService;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ServiceItemRepository serviceItemRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public Page<GetServiceItem> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return serviceItemRepository.findAllProjected(pageable);
    }

    @Override
    public void create(CreateServiceItem createServiceItem) {
        ServiceCategory category = serviceCategoryRepository.findById(createServiceItem.serviceCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory not found"));

        ServiceItem item = ServiceItem.builder()
                .code(createServiceItem.code())
                .description(createServiceItem.description())
                .parameterType(ParameterType.valueOf(createServiceItem.parameterType()))
                .serviceCategory(category)
                .build();

        createServiceItem.suppliers().forEach(s -> {
            ServiceItemSupplier supplier = ServiceItemSupplier.builder()
                    .code(s.code())
                    .description(s.description())
                    .methodology(s.methodology())
                    .accreditation(s.accreditation())
                    .price(s.price())
                    .serviceItem(item)
                    .build();

            item.getSuppliers().add(supplier);
        });

        serviceItemRepository.save(item);
    }

    @Override
    @Transactional
    public void update(UpdateServiceItem dto) {
        ServiceItem item = serviceItemRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceItem not found"));

        item.setCode(dto.code());
        item.setDescription(dto.description());
        item.setParameterType(ParameterType.valueOf(dto.parameterType()));

        if (!item.getServiceCategory().getId().equals(dto.serviceCategoryId())) {
            ServiceCategory newCategory = serviceCategoryRepository.findById(dto.serviceCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory not found"));
            item.setServiceCategory(newCategory);
        }

        serviceItemRepository.save(item);
    }

    @Override
    public void softDeleteServiceItem(Long serviceItemId) {
        ServiceItem item = serviceItemRepository.findById(serviceItemId)
                .orElseThrow(() -> new RuntimeException("ServiceItem not found"));

        item.setStatus(RecordStatus.INACTIVE);

        for (ServiceItemSupplier supplier : item.getSuppliers()) {
            supplier.setStatus(RecordStatus.INACTIVE);
        }

        serviceItemRepository.save(item);
    }

    @Override
    public GetItemDetails getDetails(Long itemId) {
        return serviceItemRepository.findItemDetailsById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceItem not found"));
    }
}