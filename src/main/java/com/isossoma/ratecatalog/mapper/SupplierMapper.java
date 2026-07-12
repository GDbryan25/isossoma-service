package com.isossoma.ratecatalog.mapper;

import com.isossoma.ratecatalog.dto.response.SupplierResponse;
import com.isossoma.ratecatalog.model.ServiceSupplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponse toSupplierResponse(ServiceSupplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .note(supplier.getNote())
                .status(supplier.getStatus())
                .build();
    }
}
