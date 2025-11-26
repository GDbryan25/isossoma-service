package com.isossoma.shared.constraints;

import com.isossoma.customer.dto.request.SaveCustomerRequest;
import com.isossoma.customer.models.DocumentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<ValidDocument, SaveCustomerRequest> {
    @Override
    public boolean isValid(SaveCustomerRequest dto, ConstraintValidatorContext context) {
        if (DocumentType.valueOf(dto.documentType()) == DocumentType.DNI) {
            return dto.documentNumber() != null && dto.documentNumber().length() == 8;
        }

        if (DocumentType.valueOf(dto.documentType()) == DocumentType.RUC) {
            return dto.documentNumber() != null && dto.documentNumber().length() == 11;
        }

        return true;
    }
}
