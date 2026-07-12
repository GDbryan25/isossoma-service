package com.isossoma.customer.mapper;

import com.isossoma.customer.dto.request.SaveCustomer;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.models.entities.Customer;
import com.isossoma.customer.models.enums.DocumentType;
import com.isossoma.customer.models.valueobjects.CustomerInformation;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerInformation toCustomerInformation(SaveCustomer createCustomerRequest) {
        DocumentType documentType = DocumentType.valueOf(createCustomerRequest.documentType());

        return new CustomerInformation(
                createCustomerRequest.name(),
                createCustomerRequest.address(),
                createCustomerRequest.contactName(),
                createCustomerRequest.contactPosition(),
                createCustomerRequest.email(),
                createCustomerRequest.cellphone(),
                documentType,
                createCustomerRequest.documentNumber(),
                createCustomerRequest.observations()
        );
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