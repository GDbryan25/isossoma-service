package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.model.ServiceItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemRepository
        extends JpaRepository<ServiceItem, Long>, JpaSpecificationExecutor<ServiceItem> {
    @EntityGraph(attributePaths = {
            "serviceCategory",
            "suppliers",
            "suppliers.supplier"
    })
    Optional<ServiceItem> findById(Long id);

}