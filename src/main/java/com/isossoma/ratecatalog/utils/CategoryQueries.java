package com.isossoma.ratecatalog.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryQueries {
    public static final String QUERY_CATEGORIES_BY_SERVICE_TYPE= """
    SELECT sc
    FROM ServiceCategory sc
    WHERE (:serviceTypeId IS NULL OR sc.serviceType.id = :serviceTypeId)
    """;
}
