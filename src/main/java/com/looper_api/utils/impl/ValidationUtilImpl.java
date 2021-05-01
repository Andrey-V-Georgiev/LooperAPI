package com.looper_api.utils.impl;

import com.looper_api.utils.ValidationUtil;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return this.validator.validate(entity).isEmpty();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> violations(T entity) {
        return this.validator.validate(entity);
    }

    @Override
    public <T> String violationsString(T entity) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<T>> violations = this.violations(entity);
        violations.forEach(
                violation -> sb.append(violation.getMessageTemplate()).append(System.lineSeparator())
        );
        return sb.toString();
    }

}