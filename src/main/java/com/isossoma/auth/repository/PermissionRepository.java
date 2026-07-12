package com.isossoma.auth.repository;

import com.isossoma.auth.models.entities.Permission;
import com.isossoma.auth.utils.PermissionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(PermissionQuery.QUERY_PERMISSIONS_BY_DESCRIPTION)
    List<Permission> findAllByOptionalName(@Param("name") String name);
}