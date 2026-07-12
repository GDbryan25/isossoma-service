package com.isossoma.ratecatalog.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SupplierQueries {
    public static final String QUERY_SUPPLIER_FILTERS = """
    SELECT s
    FROM ServiceSupplier s
    WHERE (:name IS NULL OR :name = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:status IS NULL OR s.status = :status)
    """;
}