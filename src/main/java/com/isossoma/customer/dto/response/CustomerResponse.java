package com.isossoma.customer.dto.response;

import com.isossoma.customer.models.CustomerStatus;
import com.isossoma.customer.models.DocumentType;
import lombok.Builder;

@Builder
public record CustomerResponse(
        Long id,
        String name,
        String address,
        String contact,
        String contactPosition,
        String email,
        String cellphone,
        DocumentType documentType,
        CustomerStatus customerStatus,
        String documentNumber,
        String observations
) {}