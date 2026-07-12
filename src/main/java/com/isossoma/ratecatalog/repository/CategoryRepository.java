package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.model.ServiceCategory;
import com.isossoma.ratecatalog.utils.CategoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
    @Query(CategoryQueries.QUERY_CATEGORIES_BY_SERVICE_TYPE)
    List<ServiceCategory> findByOptionalServiceTypeId(@Param("serviceTypeId") Long serviceTypeId);
}