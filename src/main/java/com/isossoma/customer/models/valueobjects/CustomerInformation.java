package com.isossoma.customer.models.valueobjects;

import com.isossoma.customer.models.enums.DocumentType;

public record CustomerInformation(
        String name,
        String address,
        String contact,
        String contactPosition,
        String email,
        String cellphone,
        DocumentType documentType,
        String documentNumber,
        String observations
) {}
