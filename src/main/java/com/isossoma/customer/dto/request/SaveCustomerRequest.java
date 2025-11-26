package com.isossoma.customer.dto.request;

import com.isossoma.customer.models.DocumentType;
import com.isossoma.shared.constraints.ValidDocument;
import com.isossoma.shared.constraints.ValueOfEnum;
import jakarta.validation.constraints.*;

@ValidDocument
public record SaveCustomerRequest(
        @NotNull(message = "El tipo de documento es requerido")
        @ValueOfEnum(enumClass = DocumentType.class, message = "Tipo de documento inválido")
        String documentType,

        @NotNull(message = "El número de documento es obligatorio")
        @NotBlank(message = "El número de documento no puede ser vacío")
        @Size(min = 8, max = 11, message = "El número de documento debe tener entre 8 y 11 carácteres")
        String documentNumber,

        @NotNull(message = "El nombre es obligatorio")
        @NotBlank(message = "El nombre no puede ser vacío")
        @Size(min = 4, message = "El nombre debe tener mínimo 4 carácteres")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras")
        String name,

        @Size(min = 4, message = "El nombre del contacto debe tener mínimo 4 carácteres")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras")
        String nameContact,

        @Size(min = 4, message = "El cargo del contacto debe tener mínimo 4 carácteres")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras")
        String contactPosition,

        @Email(message = "Email inválido")
        String email,

        @Size(min = 9, max = 9, message = "El nùmero de teléfono debe tener 9 carácteres")
        String cellphone,

        @NotNull(message = "El campo dirección es obligatorio")
        @Size(min = 5, message = "El campo dirección debe tener mas de 5 carácteres")
        @NotBlank(message = "El campo dirección no puede estar en blanco")
        String address,

        String observations
) {
    public DocumentType getDocumentTypeEnum() {
        return DocumentType.valueOf(documentType);
    }
}