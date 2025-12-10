package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.dto.response.GetSupplier;
import com.isossoma.ratecatalog.model.ServiceItemSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceItemSupplierRepository extends JpaRepository<ServiceItemSupplier, Long> {
    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetSupplier(
            s.id,
            s.code,
            s.description,
            s.methodology,
            s.accreditation,
            s.price,
            si.id
        )
        FROM ServiceItemSupplier s
        JOIN s.serviceItem si
        WHERE s.id = :id
    """)
    Optional<GetSupplier> findSupplierById(@Param("id") Long id);

    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetSupplier(
            s.id,
            s.code,
            s.description,
            s.methodology,
            s.accreditation,
            s.price,
            si.id
        )
        FROM ServiceItemSupplier s
        JOIN s.serviceItem si
    """)
    Page<GetSupplier> findAllSuppliers(Pageable pageable);
}