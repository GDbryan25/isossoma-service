package com.isossoma.ratecatalog.mapper;

import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.ratecatalog.model.ItemSupplier;
import org.springframework.stereotype.Component;

@Component
public class ItemSupplierMapper {
    public ItemSupplierResponse toItemSupplierResponse(ItemSupplier itemSupplier) {
        return ItemSupplierResponse.builder()
                .id(itemSupplier.getId())
                .price(itemSupplier.getPrice())
                .methodology(itemSupplier.getMethodology())
                .accreditation(itemSupplier.getAccreditation())
                .location(itemSupplier.getLocation())
                .status(itemSupplier.getStatus())
                .supplierDescription(itemSupplier.getSupplier().getName())
                .build();
    }
}
