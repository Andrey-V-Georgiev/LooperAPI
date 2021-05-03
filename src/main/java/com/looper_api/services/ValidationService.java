package com.looper_api.services;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationService {

    <T> boolean isValid(T entity);

    <T> Set<ConstraintViolation<T>> violations(T entity);

    <T> String violationsString(T entity);
}
