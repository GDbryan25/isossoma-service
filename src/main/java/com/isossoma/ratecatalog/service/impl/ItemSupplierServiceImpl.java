package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.filters.ItemSupplierPageableFilter;
import com.isossoma.ratecatalog.dto.request.CreateItemSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateItemSupplier;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierDetailResponse;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.ratecatalog.mapper.ItemSupplierMapper;
import com.isossoma.ratecatalog.model.ItemSupplier;
import com.isossoma.ratecatalog.model.ServiceItem;
import com.isossoma.ratecatalog.model.ServiceSupplier;
import com.isossoma.ratecatalog.repository.ItemRepository;
import com.isossoma.ratecatalog.repository.ItemSupplierRepository;
import com.isossoma.ratecatalog.repository.SupplierRepository;
import com.isossoma.ratecatalog.service.ItemSupplierService;
import com.isossoma.ratecatalog.specifications.ItemSupplierSpecification;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemSupplierServiceImpl implements ItemSupplierService {
    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;
    private final ItemSupplierRepository itemSupplierRepository;
    private final ItemSupplierMapper mapper;

    @Transactional
    @Override
    public ItemSupplierResponse update(Long id, UpdateItemSupplier request) {
        ItemSupplier itemSupplier = itemSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item Supplier not found"));

        ServiceSupplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        itemSupplier.update(
                supplier,
                request.price(),
                request.methodology(),
                request.accreditation(),
                request.location()
        );

        return mapper.toItemSupplierResponse(itemSupplier);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        ItemSupplier itemSupplier = itemSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item Supplier not found"));

        itemSupplier.deactivate();
    }

    @Transactional
    @Override
    public void reactivate(Long id) {
        ItemSupplier itemSupplier = itemSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item Supplier not found"));

        itemSupplier.reactivate();
    }

    @Override
    public Page<ItemSupplierResponse> listAll(ItemSupplierPageableFilter filter) {
        Pageable pageable = PageRequest.of(
                filter.page(),
                filter.size(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Specification<ItemSupplier> specification = ItemSupplierSpecification.filter(filter);

        return itemSupplierRepository
                .findAll(specification, pageable)
                .map(mapper::toItemSupplierResponse);
    }

    @Transactional
    @Override
    public ItemSupplierResponse create(CreateItemSupplier request) {
        ServiceItem item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        ServiceSupplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        ItemSupplier itemSupplier = item.addSupplier(
                supplier,
                request.price(),
                request.methodology(),
                request.accreditation(),
                request.location()
        );

        itemRepository.save(item);

        return mapper.toItemSupplierResponse(itemSupplier);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemSupplierDetailResponse getById(Long id) {
        return itemSupplierRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item Supplier not found"));
    }
}