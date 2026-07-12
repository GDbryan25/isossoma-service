package com.isossoma.auth.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserQueries {
    public static final String QUERY_USER_WITH_ROLES_AND_PERMISSIONS = """
            SELECT u
            FROM User u
            WHERE
                u.username = :username AND
                u.status = com.isossoma.shared.model.enums.RecordStatus.ACTIVE
    """;

    public static final String QUERY_USER_WITH_ROLES_AND_PERMISSIONS_BY_ID = """
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions
        WHERE u.id = :id
    """;

    public static final String QUERY_ALL_USERS_WITH_FILTERS = """
        SELECT new com.isossoma.auth.dto.response.user.UserResponse(
            u.id,
            u.username,
            u.email,
            u.firstname,
            u.lastname,
            u.status
        )
        FROM User u
        WHERE (:status IS NULL OR u.status = :status)
          AND (:firstname IS NULL OR :firstname = '' OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :firstname, '%')))
          AND (:lastname IS NULL OR :lastname = '' OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :lastname, '%')))
          AND u.deletedAt IS NULL
        """;
}