package com.example.apiuser.Rules;

import com.example.apiuser.Rules.Implements.UniqueValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "The value must be unique";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    Class<?> entityClass();
    String field();
}