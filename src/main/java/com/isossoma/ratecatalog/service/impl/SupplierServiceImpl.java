package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.GetSupplier;
import com.isossoma.ratecatalog.model.RecordStatus;
import com.isossoma.ratecatalog.model.ServiceItem;
import com.isossoma.ratecatalog.model.ServiceItemSupplier;
import com.isossoma.ratecatalog.repository.ServiceItemRepository;
import com.isossoma.ratecatalog.repository.ServiceItemSupplierRepository;
import com.isossoma.ratecatalog.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final ServiceItemSupplierRepository supplierRepository;
    private final ServiceItemRepository serviceItemRepository;

    @Override
    @Transactional
    public Long create(CreateSupplier dto) {

        ServiceItem serviceItem = serviceItemRepository.findById(dto.serviceItemId())
                .orElseThrow(() -> new RuntimeException("ServiceItem not found"));

        ServiceItemSupplier supplier = ServiceItemSupplier.builder()
                .code(dto.code())
                .description(dto.description())
                .methodology(dto.methodology())
                .accreditation(dto.accreditation())
                .price(dto.price())
                .serviceItem(serviceItem)
                .build();

        supplierRepository.save(supplier);
        return supplier.getId();
    }

    @Override
    @Transactional
    public void update(UpdateSupplier dto) {

        ServiceItemSupplier supplier = supplierRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setCode(dto.code());
        supplier.setDescription(dto.description());
        supplier.setMethodology(dto.methodology());
        supplier.setAccreditation(dto.accreditation());
        supplier.setPrice(dto.price());

        supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ServiceItemSupplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setStatus(RecordStatus.INACTIVE);
        supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public GetSupplier get(Long id) {
        return supplierRepository.findSupplierById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetSupplier> getAll(int page, int size) {
        return supplierRepository.findAllSuppliers(PageRequest.of(page, size));
    }
}