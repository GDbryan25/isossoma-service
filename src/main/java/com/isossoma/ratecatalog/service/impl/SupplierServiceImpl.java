package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.filters.SupplierFilter;
import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.SupplierResponse;
import com.isossoma.ratecatalog.mapper.SupplierMapper;
import com.isossoma.ratecatalog.model.ServiceSupplier;
import com.isossoma.ratecatalog.repository.SupplierRepository;
import com.isossoma.ratecatalog.service.SupplierService;
import com.isossoma.shared.exception.ConflictException;
import com.isossoma.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    @Override
    @Transactional
    public SupplierResponse create(CreateSupplier createSupplier) {
        if(repository.existsByName(createSupplier.name())) {
            throw new ConflictException("Proveedor ya existe");
        }

        ServiceSupplier supplier = new ServiceSupplier(createSupplier.name(), createSupplier.note());

        return mapper.toSupplierResponse(repository.save(supplier));
    }

    @Override
    @Transactional
    public SupplierResponse update(Long id, UpdateSupplier updateSupplier) {
        ServiceSupplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el proveedor"));

        supplier.update(updateSupplier.name(), updateSupplier.note());

        return mapper.toSupplierResponse(supplier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ServiceSupplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el proveedor"));

        supplier.deactivate();
    }

    @Override
    @Transactional
    public void reactivate(Long id) {
        ServiceSupplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el proveedor"));

        supplier.reactivate();
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse findById(Long id) {
        ServiceSupplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        return mapper.toSupplierResponse(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponse> findAll(SupplierFilter filter) {
        Pageable pageable = PageRequest.of(filter.page(), filter.size(), Sort.by("id").descending());

        Page<ServiceSupplier> suppliers = repository.findAllByFilters(filter.name(), filter.status(), pageable);

        return suppliers.map(mapper::toSupplierResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> findAllNoPaginated() {
        return repository.findAll(Sort.by("id").descending())
                .stream()
                .map(mapper::toSupplierResponse)
                .toList();
    }
}