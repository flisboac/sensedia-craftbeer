package com.beerhouse.common.core.services;

import com.beerhouse.common.core.dto.AppValidationExceptionDto;
import com.beerhouse.common.core.dto.ExceptionDto;
import com.beerhouse.common.core.lang.AppValidationException;

import javax.servlet.http.HttpServletRequest;

public interface TranslationService {
    ExceptionDto fromExceptionToDto(HttpServletRequest req, Exception ex);
    AppValidationExceptionDto fromExceptionToDto(HttpServletRequest req, AppValidationException ex);
}
