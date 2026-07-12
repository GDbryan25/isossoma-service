package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.filters.SupplierFilter;
import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.SupplierResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface SupplierService {
    SupplierResponse create(CreateSupplier createSupplier);
    SupplierResponse update(Long id, UpdateSupplier updateSupplier);
    void delete(Long id);
    void reactivate(Long id);
    SupplierResponse findById(Long id);
    Page<SupplierResponse> findAll(SupplierFilter filter);
    List<SupplierResponse> findAllNoPaginated();
}