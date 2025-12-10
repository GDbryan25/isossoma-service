package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.GetSupplier;
import org.springframework.data.domain.Page;

public interface SupplierService {
    Long create(CreateSupplier dto);
    void update(UpdateSupplier dto);
    void delete(Long id);
    GetSupplier get(Long id);
    Page<GetSupplier> getAll(int page, int size);
}