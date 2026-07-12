package com.isossoma.auth.repository;

import com.isossoma.auth.dto.response.user.UserResponse;
import com.isossoma.auth.models.entities.User;
import com.isossoma.auth.utils.UserQueries;
import com.isossoma.shared.model.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameAndDeletedAtIsNullAndIdNot(String username, Long id);
    boolean existsByEmailAndDeletedAtIsNullAndIdNot(String email, Long id);
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    @Query(UserQueries.QUERY_USER_WITH_ROLES_AND_PERMISSIONS)
    Optional<User> findByUsernameWithRolesAndPermissions(@Param("username") String username);
    boolean existsByUsernameAndDeletedAtIsNull(String username);
    boolean existsByEmailAndDeletedAtIsNull(String email);
    @Query(UserQueries.QUERY_USER_WITH_ROLES_AND_PERMISSIONS_BY_ID)
    Optional<User> findByIdWithRolesAndPermissions(@Param("id") Long id);
    @Query(UserQueries.QUERY_ALL_USERS_WITH_FILTERS)
    Page<UserResponse> findUsers(
            @Param("status") RecordStatus status,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            Pageable pageable
    );
}