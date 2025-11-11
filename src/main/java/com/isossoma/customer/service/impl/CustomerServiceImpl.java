package com.isossoma.customer.service.impl;

import com.isossoma.customer.models.Customer;
import com.isossoma.customer.repository.CustomerRepository;
import com.isossoma.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    @Override
    public void create(Customer customer) {
        repository.save(customer);
    }
}