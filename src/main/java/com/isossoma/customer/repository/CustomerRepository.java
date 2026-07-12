package com.isossoma.customer.repository;

import com.isossoma.customer.models.entities.Customer;
import com.isossoma.customer.utils.CustomerQueries;
import com.isossoma.shared.model.enums.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByDocumentNumber(String documentNumber);
    @Query(CustomerQueries.QUERY_FIND_CUSTOMERS)
    Page<Customer> findAll(@Param("status") RecordStatus status, @Param("name") String name, Pageable pageable);
}