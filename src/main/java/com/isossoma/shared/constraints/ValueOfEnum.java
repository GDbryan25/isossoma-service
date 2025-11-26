package com.isossoma.shared.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
@Documented
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "El valor debe ser uno de los valores válidos del enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}