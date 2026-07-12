package com.isossoma.ratecatalog.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceItemQueries {
    public static final String QUERY_ITEM_SUPPLIER_LIST = """
        SELECT new com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse(
            i.id,
            i.price,
            i.methodology,
            i.accreditation,
            i.location,
            i.status,
            s.name
        )
        FROM ItemSupplier i
        JOIN i.supplier s
        WHERE i.serviceItem.id = :itemId
          AND i.status = :status
    """;

    public static final String QUERY_ITEM_SUPPLIER_DETAIL = """
        SELECT new com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierDetailResponse(
            i.id,
            i.price,
            i.methodology,
            i.accreditation,
            i.location,
            i.status,
            si.id,
            s.id,
            s.name
        )
        FROM ItemSupplier i
        JOIN i.serviceItem si
        JOIN i.supplier s
        WHERE i.id = :id
    """;
}
