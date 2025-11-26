package com.isossoma.customer.repository;

import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}