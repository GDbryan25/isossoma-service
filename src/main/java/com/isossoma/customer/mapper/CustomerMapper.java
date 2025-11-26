package com.isossoma.customer.mapper;

import com.isossoma.customer.dto.request.SaveCustomerRequest;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomerEntity(SaveCustomerRequest createCustomerRequest) {
        return Customer.builder()
                .name(createCustomerRequest.name())
                .address(createCustomerRequest.address())
                .contact(createCustomerRequest.nameContact())
                .contactPosition(createCustomerRequest.contactPosition())
                .email(createCustomerRequest.email())
                .cellphone(createCustomerRequest.cellphone())
                .documentType(createCustomerRequest.getDocumentTypeEnum())
                .documentNumber(createCustomerRequest.documentNumber())
                .observations(createCustomerRequest.observations())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .contact(customer.getContact())
                .contactPosition(customer.getContactPosition())
                .email(customer.getEmail())
                .cellphone(customer.getCellphone())
                .documentType(customer.getDocumentType())
                .customerStatus(customer.getCustomerStatus())
                .documentNumber(customer.getDocumentNumber())
                .observations(customer.getObservations())
                .build();
    }
}