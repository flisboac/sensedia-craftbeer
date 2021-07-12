package com.beerhouse.common.entities.services.impl;

import com.beerhouse.common.core.lang.AppValidationException;
import com.beerhouse.common.entities.services.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class SimpleEntityValidator implements EntityValidator {

    @Autowired
    private Validator validator;

    @Override
    public <T> void validate(T entity, Class<?>[] groups) throws AppValidationException {
        var constraintViolations = validator.validate(entity, groups);
        if (!constraintViolations.isEmpty()) {
            throw new AppValidationException(constraintViolations);
        }
    }
}
