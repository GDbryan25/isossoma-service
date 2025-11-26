package com.isossoma.customer.service.impl;

import com.isossoma.customer.dto.request.SaveCustomerRequest;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.mapper.CustomerMapper;
import com.isossoma.customer.models.Customer;
import com.isossoma.customer.models.DocumentType;
import com.isossoma.customer.repository.CustomerRepository;
import com.isossoma.customer.service.CustomerService;
import com.isossoma.shared.exception.ConflictException;
import com.isossoma.shared.exception.ResourceNotFoundException;
import com.isossoma.shared.message.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse create(SaveCustomerRequest createCustomerRequest) {
        if(repository.existsByDocumentNumber(createCustomerRequest.documentNumber())) {
            throw new ConflictException(ErrorMessages.CUSTOMER_DOCUMENT_DUPLICATED);
        }

        Customer customer = repository.save(mapper.toCustomerEntity(createCustomerRequest));

        return mapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse update(Long id, SaveCustomerRequest updateCustomerRequest) {
        if(repository.existsByDocumentNumber(updateCustomerRequest.documentNumber())) {
            throw new ConflictException(ErrorMessages.CUSTOMER_DOCUMENT_DUPLICATED);
        }

        Customer customerFound = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CUSTOMER_NOT_FOUND));

        setValues(customerFound, updateCustomerRequest);

        Customer customer = repository.save(customerFound);

        return mapper.toCustomerResponse(customer);
    }

    @Override
    public Long delete(Long id) {
        repository.deleteById(id);

        return id;
    }

    @Override
    public Page<CustomerResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Customer> customers = repository.findAll(pageable);

        return customers.map(mapper::toCustomerResponse);
    }

    private void setValues(Customer customer, SaveCustomerRequest customerUpdateRequest) {
        customer.setName(customerUpdateRequest.name());
        customer.setAddress(customerUpdateRequest.address());
        customer.setContact(customerUpdateRequest.nameContact());
        customer.setContactPosition(customerUpdateRequest.contactPosition());
        customer.setEmail(customerUpdateRequest.email());
        customer.setCellphone(customerUpdateRequest.cellphone());
        customer.setDocumentType(DocumentType.valueOf(customerUpdateRequest.documentType()));
        customer.setDocumentNumber(customerUpdateRequest.documentNumber());
        customer.setObservations(customerUpdateRequest.observations());
    }
}