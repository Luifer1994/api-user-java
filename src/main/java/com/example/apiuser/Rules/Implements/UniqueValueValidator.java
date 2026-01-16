package com.example.apiuser.Rules.Implements;

import com.example.apiuser.Rules.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String field;

    @Override
    public void initialize(UniqueValue annotation) {
        this.entityClass = annotation.entityClass();
        this.field = annotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        String query = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + field + " = :value";

        long count = (long) entityManager.createQuery(query)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}