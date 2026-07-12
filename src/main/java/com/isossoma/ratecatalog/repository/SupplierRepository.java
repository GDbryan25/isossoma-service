package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.model.ServiceSupplier;
import com.isossoma.ratecatalog.utils.SupplierQueries;
import com.isossoma.shared.model.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<ServiceSupplier, Long> {
    boolean existsByName(String name);

    @Query(SupplierQueries.QUERY_SUPPLIER_FILTERS)
    Page<ServiceSupplier> findAllByFilters(
            @Param("name") String name,
            @Param("status") RecordStatus status,
            Pageable pageable);
}