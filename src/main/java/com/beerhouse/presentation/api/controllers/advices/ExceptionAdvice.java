package com.beerhouse.presentation.api.controllers.advices;

import com.beerhouse.common.core.lang.AppValidationException;
import com.beerhouse.common.core.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionAdvice {
    @Autowired
    private TranslationService translationService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException exception) {
        var body = translationService.fromExceptionToDto(req, exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AppValidationException.class)
    public ResponseEntity<?> handleAppValidationException(HttpServletRequest req, AppValidationException exception) {
        var body = translationService.fromExceptionToDto(req, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<?> handleJpaObjectRetrievalFailureException(HttpServletRequest req, JpaObjectRetrievalFailureException exception) {
        var cause = exception.getCause();
        if (cause instanceof EntityNotFoundException) {
            return handleEntityNotFoundException(req, (EntityNotFoundException) cause);
        }
        return handleException(req, exception);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(HttpServletRequest req, Exception exception) {
        var body = translationService.fromExceptionToDto(req, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
