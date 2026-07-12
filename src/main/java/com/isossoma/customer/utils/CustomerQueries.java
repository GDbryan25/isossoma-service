package com.isossoma.customer.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerQueries {
    public static final String QUERY_FIND_CUSTOMERS = """
    SELECT c
    FROM Customer c
    WHERE (:status IS NULL OR c.customerStatus = :status)
      AND (:name IS NULL OR :name = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND c.deletedAt IS NULL
    """;
}
