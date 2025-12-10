package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.dto.response.GetItemDetails;
import com.isossoma.ratecatalog.dto.response.GetServiceItem;
import com.isossoma.ratecatalog.model.ServiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetServiceItem(
            si.id,
            si.code,
            si.description,
            si.parameterType,
            sc.code
        )
        FROM ServiceItem si
        JOIN si.serviceCategory sc
    """)
    Page<GetServiceItem> findAllProjected(Pageable pageable);

    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetItemDetails(
            si.id,
            si.code,
            si.description,
            si.parameterType,
            sc.id,
            st.id
        )
        FROM ServiceItem si
        JOIN si.serviceCategory sc
        JOIN sc.serviceType st
        WHERE si.id = :itemId
    """)
    Optional<GetItemDetails> findItemDetailsById(@Param("itemId") Long itemId);
}