package com.isossoma.shared.constraints;

import com.isossoma.customer.dto.request.SaveCustomer;
import com.isossoma.customer.models.enums.DocumentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<ValidDocument, SaveCustomer> {
    @Override
    public boolean isValid(SaveCustomer dto, ConstraintValidatorContext context) {
        if (DocumentType.valueOf(dto.documentType()) == DocumentType.DNI) {
            return dto.documentNumber() != null && dto.documentNumber().length() == 8;
        }

        if (DocumentType.valueOf(dto.documentType()) == DocumentType.RUC) {
            return dto.documentNumber() != null && dto.documentNumber().length() == 11;
        }

        return true;
    }
}
