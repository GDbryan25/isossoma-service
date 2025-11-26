package com.isossoma.customer.service;

import com.isossoma.customer.dto.request.SaveCustomerRequest;
import com.isossoma.customer.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(SaveCustomerRequest createCustomerRequest);
    CustomerResponse update(Long id, SaveCustomerRequest updateCustomerRequest);
    Long delete(Long id);
    Page<CustomerResponse> findAll(int page, int size);
}