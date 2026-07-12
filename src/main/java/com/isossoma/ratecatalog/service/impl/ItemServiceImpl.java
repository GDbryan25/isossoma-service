package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.filters.ServiceItemFilter;
import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.CreateServiceItemSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.item.ItemResponse;
import com.isossoma.ratecatalog.dto.response.item.ItemWithSupplierResponse;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.ratecatalog.mapper.ItemMapper;
import com.isossoma.ratecatalog.model.ServiceCategory;
import com.isossoma.ratecatalog.model.ServiceItem;
import com.isossoma.ratecatalog.enums.ParameterType;
import com.isossoma.ratecatalog.model.ServiceSupplier;
import com.isossoma.ratecatalog.repository.CategoryRepository;
import com.isossoma.ratecatalog.repository.ItemRepository;
import com.isossoma.ratecatalog.repository.SupplierRepository;
import com.isossoma.ratecatalog.service.ItemService;
import com.isossoma.ratecatalog.specifications.ServiceItemSpecification;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ItemMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> search(ServiceItemFilter filter, Pageable pageable) {
        Page<ServiceItem> page = itemRepository.findAll(ServiceItemSpecification.withFilters(filter), pageable);

        return page.map(mapper::toResponse);
    }

    @Transactional
    @Override
    public ItemResponse create(CreateServiceItem createServiceItem) {
        ServiceCategory category = categoryRepository.findById(createServiceItem.serviceCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        ServiceItem serviceItem = new ServiceItem(
                createServiceItem.description(),
                ParameterType.valueOf(createServiceItem.parameterType()),
                createServiceItem.note(),
                category
        );

        List<Long> supplierIds = createServiceItem.suppliers()
                .stream()
                .map(CreateServiceItemSupplier::supplierId)
                .toList();

        Map<Long, ServiceSupplier> suppliersMap =
                supplierRepository.findAllById(supplierIds)
                        .stream()
                        .collect(Collectors.toMap(
                                ServiceSupplier::getId,
                                Function.identity()
                        ));

        for (CreateServiceItemSupplier supplierRequest : createServiceItem.suppliers()) {
            ServiceSupplier supplier = Optional.ofNullable(suppliersMap.get(supplierRequest.supplierId()))
                            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + supplierRequest.supplierId()));

            serviceItem.addSupplier(
                    supplier,
                    supplierRequest.price(),
                    supplierRequest.methodology(),
                    supplierRequest.accreditation(),
                    supplierRequest.location()
            );
        }

        ServiceItem item = itemRepository.save(serviceItem);

        return mapper.toResponse(item);
    }

    @Override
    @Transactional
    public ItemResponse update(Long id, UpdateServiceItem request) {
        ServiceItem item = itemRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Service item not found"));

        ServiceCategory category = categoryRepository.findById(request.serviceCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        item.update(request.description(), request.note(), request.parameterType(), category);

        return  mapper.toResponse(itemRepository.save(item));
    }

    @Override
    @Transactional
    public void softDeleteServiceItem(Long serviceItemId) {
        ServiceItem item = itemRepository.findById(serviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Service item not found"));

        item.deactivate();
    }

    @Override
    @Transactional
    public void reactivateServiceItem(Long serviceItemId) {
        ServiceItem item = itemRepository.findById(serviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Service item not found"));

        item.reactivate();
    }

    @Transactional(readOnly = true)
    @Override
    public ItemWithSupplierResponse getItemWithSuppliers(Long id) {
        ServiceItem item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        List<ItemSupplierResponse> suppliers = item.getSuppliers()
                .stream()
                .map(s -> new ItemSupplierResponse(
                        s.getId(),
                        s.getPrice(),
                        s.getMethodology(),
                        s.getAccreditation(),
                        s.getLocation(),
                        s.getStatus(),
                        s.getSupplier().getName()
                ))
                .toList();

        return new ItemWithSupplierResponse(
                item.getServiceCategory().getId(),
                item.getDescription(),
                item.getParameterType(),
                item.getNote(),
                suppliers
        );
    }
}