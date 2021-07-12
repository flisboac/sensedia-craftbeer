package com.beerhouse.common.core.lang;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

public class AppValidationException extends AppException {
    private final Set<? extends ConstraintViolation<?>> constraintViolations;

    public AppValidationException() {
        this.constraintViolations = Collections.emptySet();
    }

    public AppValidationException(String message) {
        super(message);
        this.constraintViolations = Collections.emptySet();
    }

    public AppValidationException(String message, Throwable cause) {
        super(message, cause);
        this.constraintViolations = Collections.emptySet();
    }

    public AppValidationException(Throwable cause) {
        super(cause);
        this.constraintViolations = Collections.emptySet();
    }

    public AppValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.constraintViolations = Collections.emptySet();
    }

    public AppValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    public AppValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message);
        this.constraintViolations = constraintViolations;
    }

    public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }
}
