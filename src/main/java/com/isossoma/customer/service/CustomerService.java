package com.isossoma.customer.service;

import com.isossoma.customer.dto.filter.CustomerFilter;
import com.isossoma.customer.dto.request.SaveCustomer;
import com.isossoma.customer.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(SaveCustomer createCustomer);
    CustomerResponse update(Long id, SaveCustomer updateCustomer);
    Long delete(Long id);
    Long reactivate(Long id);
    Page<CustomerResponse> findAll(CustomerFilter filter);
    CustomerResponse findById(Long id);
}