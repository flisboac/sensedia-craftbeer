package com.beerhouse.common.entities.services;

import com.beerhouse.common.core.lang.AppValidationException;

public interface EntityValidator {
    <T> void validate(T entity, Class<?>... groups) throws AppValidationException;
}
