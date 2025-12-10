package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.request.CreateServiceCategory;
import com.isossoma.ratecatalog.dto.request.CreateServiceType;
import com.isossoma.ratecatalog.dto.request.UpdateServiceType;
import com.isossoma.ratecatalog.dto.response.GetAllServiceType;
import com.isossoma.ratecatalog.model.*;
import com.isossoma.ratecatalog.repository.ServiceTypeRepository;
import com.isossoma.ratecatalog.service.OperationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public void saveServiceTypeWithCategories(CreateServiceType createServiceType) {
        ServiceType type = serviceTypeRepository.findById(createServiceType.serviceId())
                .orElseGet(() -> ServiceType.builder()
                        .code(createServiceType.code())
                        .description(createServiceType.description())
                        .build());

        for (CreateServiceCategory catReq : createServiceType.categories()) {

            ServiceCategory category = ServiceCategory.builder()
                    .code(catReq.code())
                    .description(catReq.description())
                    .serviceType(type)
                    .build();

            type.getCategories().add(category);
        }

        serviceTypeRepository.save(type);
    }

    @Override
    public void editServiceTypeWithCategories(UpdateServiceType updateServiceType) {
        ServiceType type = serviceTypeRepository.findById(updateServiceType.serviceId())
                .orElseGet(() -> ServiceType.builder()
                        .code(updateServiceType.code())
                        .description(updateServiceType.description())
                        .build());

        if(type.getId() != null) {
            type.setCode(updateServiceType.code());
            type.setDescription(updateServiceType.description());
        }

        ServiceCategory category = ServiceCategory.builder()
                .id(updateServiceType.category().id())
                .code(updateServiceType.category().code())
                .description(updateServiceType.category().description())
                .serviceType(type)
                .build();

        type.getCategories().add(category);

        serviceTypeRepository.save(type);
    }

    @Transactional
    @Override
    public void softDeleteServiceType(Long serviceTypeId) {
        ServiceType type = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new RuntimeException("ServiceType not found"));

        type.setStatus(RecordStatus.INACTIVE);

        for (ServiceCategory category : type.getCategories()) {
            category.setStatus(RecordStatus.INACTIVE);

            for (ServiceItem item : category.getItems()) {
                item.setStatus(RecordStatus.INACTIVE);

                for (ServiceItemSupplier supplier : item.getSuppliers()) {
                    supplier.setStatus(RecordStatus.INACTIVE);
                }
            }
        }

        serviceTypeRepository.save(type);
    }

    @Override
    public List<GetAllServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll()
                .stream()
                .map(item -> new GetAllServiceType(
                        item.getId(),
                        item.getCode(),
                        item.getDescription()
                ))
                .toList();
    }
}