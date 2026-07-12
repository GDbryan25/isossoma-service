package com.isossoma.customer.service.impl;

import com.isossoma.customer.dto.filter.CustomerFilter;
import com.isossoma.customer.dto.request.SaveCustomer;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.mapper.CustomerMapper;
import com.isossoma.customer.models.entities.Customer;
import com.isossoma.customer.models.valueobjects.CustomerInformation;
import com.isossoma.customer.repository.CustomerRepository;
import com.isossoma.customer.service.CustomerService;
import com.isossoma.shared.exception.ConflictException;
import com.isossoma.shared.exception.ResourceNotFoundException;
import com.isossoma.shared.message.ErrorMessages;
import com.isossoma.shared.model.enums.RecordStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse create(SaveCustomer createCustomer) {
        if(repository.existsByDocumentNumber(createCustomer.documentNumber())) {
            throw new ConflictException(ErrorMessages.CUSTOMER_DOCUMENT_DUPLICATED);
        }

        CustomerInformation customerInformation = mapper.toCustomerInformation(createCustomer);

        Customer customer = repository.save(new Customer(customerInformation));

        return mapper.toCustomerResponse(customer);
    }

    @Transactional
    @Override
    public CustomerResponse update(Long id, SaveCustomer updateCustomer) {
        Customer customerFound = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND));

        customerFound.update(mapper.toCustomerInformation(updateCustomer));

        return mapper.toCustomerResponse(customerFound);
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        Customer customerFound = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND));

        customerFound.deactivate();

        return id;
    }

    @Transactional
    @Override
    public Long reactivate(Long id) {
        Customer customerFound = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND));

        customerFound.reactivate();

        return id;
    }

    @Override
    public Page<CustomerResponse> findAll(CustomerFilter filter) {
        Pageable pageable = PageRequest.of(
                filter.page(),
                filter.size(),
                Sort.by("id").descending()
        );

        return repository.findAll(filter.status(), filter.name(), pageable)
                .map(mapper::toCustomerResponse);
    }

    @Override
    public CustomerResponse findById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND));

        return mapper.toCustomerResponse(customer);
    }
}