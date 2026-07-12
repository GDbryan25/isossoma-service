package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.filters.ServiceItemFilter;
import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.item.ItemResponse;
import com.isossoma.ratecatalog.dto.response.item.ItemWithSupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    Page<ItemResponse> search(ServiceItemFilter filter, Pageable pageable);
    ItemResponse create(CreateServiceItem createServiceItem);
    ItemResponse update(Long id, UpdateServiceItem dto);
    void softDeleteServiceItem(Long serviceItemId);
    void reactivateServiceItem(Long serviceItemId);
    ItemWithSupplierResponse getItemWithSuppliers(Long id);
}