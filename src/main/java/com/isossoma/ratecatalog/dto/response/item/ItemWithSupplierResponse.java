package com.isossoma.ratecatalog.dto.response.item;

import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.ratecatalog.enums.ParameterType;
import java.util.List;

public record ItemWithSupplierResponse(
        Long categoryId,
        String description,
        ParameterType parameterType,
        String note,
        List<ItemSupplierResponse> suppliers
) {}