package com.beerhouse.common.core.services.impl;

import com.beerhouse.common.core.dto.AppValidationExceptionDto;
import com.beerhouse.common.core.dto.ConstraintViolationDto;
import com.beerhouse.common.core.dto.ExceptionDto;
import com.beerhouse.common.core.lang.AppValidationException;
import com.beerhouse.common.core.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
public class TranslationServiceImpl implements TranslationService {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public ExceptionDto fromExceptionToDto(HttpServletRequest req, Exception ex) {
        var locale = localeResolver.resolveLocale(req);
        var code = ex.getClass().getName();
        var messageKey = ex.getMessage();
        var message = messageSource.getMessage(messageKey, null, messageKey, locale);
        var dto = new ExceptionDto();
        dto.setCode(code);
        dto.setMessage(message);
        return dto;
    }

    @Override
    public AppValidationExceptionDto fromExceptionToDto(HttpServletRequest req, AppValidationException ex) {
        var locale = localeResolver.resolveLocale(req);
        var code = ex.getClass().getName();
        var messageKey = ex.getMessage();
        var message = messageSource.getMessage(messageKey, null, messageKey, locale);
        var dto = new AppValidationExceptionDto();
        var violations = ex.getConstraintViolations().stream()
                .map(violation -> {
                    var violationDto = new ConstraintViolationDto();
                    violationDto.setProperty(violation.getPropertyPath().toString());
                    violationDto.setMessage(violation.getMessage());
                    return violationDto;
                })
                .collect(Collectors.toList());
        dto.setCode(code);
        dto.setMessage(message);
        dto.setViolations(violations);
        return dto;
    }
}
