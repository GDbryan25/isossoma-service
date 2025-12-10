package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.GetItemDetails;
import com.isossoma.ratecatalog.dto.response.GetServiceItem;
import org.springframework.data.domain.Page;

public interface ItemService {
    Page<GetServiceItem> getAll(Integer page, Integer size);
    void create(CreateServiceItem createServiceItem);
    void update(UpdateServiceItem dto);
    void softDeleteServiceItem(Long serviceItemId);
    GetItemDetails getDetails(Long itemId);
}