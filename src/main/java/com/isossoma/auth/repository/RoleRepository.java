package com.isossoma.auth.repository;

import com.isossoma.auth.models.entities.Role;
import com.isossoma.auth.utils.RoleQueries;
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
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @EntityGraph(attributePaths = "permissions")
    Optional<Role> findWithPermissionsById(Long id);

    @Query(RoleQueries.QUERY_ROLE_FILTER)
    Page<Role> findAll(@Param("status") RecordStatus status, @Param("name") String name, Pageable pageable);
}