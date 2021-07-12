package com.beerhouse.common.core.dto;

import java.util.List;

public class AppValidationExceptionDto extends ExceptionDto {
    private List<ConstraintViolationDto> violations;

    public List<ConstraintViolationDto> getViolations() {
        return violations;
    }

    public void setViolations(List<ConstraintViolationDto> violations) {
        this.violations = violations;
    }
}
