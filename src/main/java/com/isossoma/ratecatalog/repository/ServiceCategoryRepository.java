package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.dto.response.GetServiceCategory;
import com.isossoma.ratecatalog.model.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetServiceCategory(
            sc.id,
            sc.code,
            sc.description,
            sc.status,
            st.description,
            st.id
        )
        FROM ServiceCategory sc
        JOIN sc.serviceType st
    """)
    Page<GetServiceCategory> findAllProjected(Pageable pageable);

    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetServiceCategory(
            sc.id,
            sc.code,
            sc.description,
            sc.status,
            st.description,
            st.id
        )
        FROM ServiceCategory sc
        JOIN sc.serviceType st
        WHERE sc.id = :id
    """)
    Optional<GetServiceCategory> findCategoryProjectedById(Long id);

    @Query("""
        SELECT new com.isossoma.ratecatalog.dto.response.GetServiceCategory(
            sc.id,
            sc.code,
            sc.description,
            sc.status,
            st.code,
            st.id
        )
        FROM ServiceCategory sc
        JOIN sc.serviceType st
        WHERE st.id = :serviceTypeId
    """)
    List<GetServiceCategory> findAllByServiceTypeId(Long serviceTypeId);
}