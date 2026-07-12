package com.isossoma.ratecatalog.mapper;

import com.isossoma.ratecatalog.dto.response.item.ItemResponse;
import com.isossoma.ratecatalog.model.ServiceItem;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemResponse toResponse(ServiceItem item) {
        return ItemResponse.builder()
                .id(item.getId())
                .description(item.getDescription())
                .parameterType(item.getParameterType())
                .note(item.getNote())
                .status(item.getStatus())
                .categoryDescription(item.getServiceCategory().getDescription())
                .build();
    }
}