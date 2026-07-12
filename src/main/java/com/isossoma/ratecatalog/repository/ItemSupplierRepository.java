package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierDetailResponse;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.shared.model.enums.RecordStatus;
import com.isossoma.ratecatalog.model.ItemSupplier;
import com.isossoma.ratecatalog.utils.ServiceItemQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemSupplierRepository extends JpaRepository<ItemSupplier, Long>,
        JpaSpecificationExecutor<ItemSupplier> {
    @Query(ServiceItemQueries.QUERY_ITEM_SUPPLIER_LIST)
    Page<ItemSupplierResponse> findByItemIdAndStatus(
            @Param("itemId") Long itemId,
            @Param("status") RecordStatus status,
            Pageable pageable
    );

    @Query(ServiceItemQueries.QUERY_ITEM_SUPPLIER_DETAIL)
    Optional<ItemSupplierDetailResponse> findDetailById(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = "supplier")
    Page<ItemSupplier> findAll(Specification<ItemSupplier> spec, Pageable pageable);
}
