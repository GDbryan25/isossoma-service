package com.isossoma.ratecatalog.repository;

import com.isossoma.ratecatalog.model.ServiceItemSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceItemSupplierRepository extends JpaRepository<ServiceItemSupplier, String> {}