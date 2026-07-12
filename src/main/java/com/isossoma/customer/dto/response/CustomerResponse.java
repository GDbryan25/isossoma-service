package com.isossoma.customer.dto.response;

import com.isossoma.customer.models.enums.DocumentType;
import com.isossoma.shared.model.enums.RecordStatus;
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
        RecordStatus customerStatus,
        String documentNumber,
        String observations
) {}