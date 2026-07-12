package com.isossoma.auth.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PermissionQuery {
    public static final String QUERY_PERMISSIONS_BY_DESCRIPTION = """
    SELECT p FROM Permission p
    WHERE (:name IS NULL OR :name = ''
      OR LOWER(COALESCE(p.description, '')) LIKE LOWER(CONCAT('%', :name, '%'))
      OR LOWER(COALESCE(p.code, '')) LIKE LOWER(CONCAT('%', :name, '%'))
      OR LOWER(COALESCE(p.menuKey, '')) LIKE LOWER(CONCAT('%', :name, '%'))
      OR LOWER(COALESCE(p.submenuKey, '')) LIKE LOWER(CONCAT('%', :name, '%'))
      OR LOWER(COALESCE(p.actionKey, '')) LIKE LOWER(CONCAT('%', :name, '%')))
    """;
}
