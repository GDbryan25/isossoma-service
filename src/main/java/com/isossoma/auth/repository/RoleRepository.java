package com.isossoma.auth.repository;

import com.isossoma.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndDeletedAtIsNull(String name);
    boolean existsByName(String name);
}