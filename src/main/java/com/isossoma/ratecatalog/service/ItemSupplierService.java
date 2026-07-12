package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.filters.ItemSupplierPageableFilter;
import com.isossoma.ratecatalog.dto.request.CreateItemSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateItemSupplier;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierDetailResponse;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import org.springframework.data.domain.Page;

public interface ItemSupplierService {
    ItemSupplierResponse update(Long id, UpdateItemSupplier request);
    void delete(Long id);
    void reactivate(Long id);
    Page<ItemSupplierResponse> listAll(ItemSupplierPageableFilter filter);
    ItemSupplierResponse create(CreateItemSupplier request);
    ItemSupplierDetailResponse getById(Long id);
}