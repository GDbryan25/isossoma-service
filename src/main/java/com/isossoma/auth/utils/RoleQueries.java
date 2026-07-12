package com.isossoma.auth.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleQueries {
    public static final String QUERY_ROLE_FILTER = """
    SELECT r
    FROM Role r
    WHERE (:status IS NULL OR r.status = :status)
      AND (:name IS NULL OR :name = '' OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """;
}
