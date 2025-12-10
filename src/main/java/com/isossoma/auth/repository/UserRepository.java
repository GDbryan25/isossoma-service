package com.isossoma.auth.repository;

import com.isossoma.auth.dto.response.GetAllUsers;
import com.isossoma.auth.models.User;
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
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.isActive = true")
    Optional<User> findByUsernameWithRolesAndPermissions(@Param("username") String username);
    Optional<User> findByUsernameAndDeletedAtIsNull(String username);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
    boolean existsByUsernameAndDeletedAtIsNull(String username);
    boolean existsByEmailAndDeletedAtIsNull(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("""
        SELECT new com.isossoma.auth.dto.response.GetAllUsers(
            u.id, u.username, u.email, u.isActive, u.isLocked
        )
        FROM User u
    """)
    Page<GetAllUsers> findAllUsers(Pageable pageable);
}