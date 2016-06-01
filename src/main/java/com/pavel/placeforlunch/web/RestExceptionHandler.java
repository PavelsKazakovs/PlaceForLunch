package com.pavel.placeforlunch.web;

import com.pavel.placeforlunch.util.exception.ErrorInfo;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import com.pavel.placeforlunch.util.exception.ValidationErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, ResourceNotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ValidationErrorInfo bindValidationError(HttpServletRequest req, MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage()).collect(Collectors.toList());
        return logAndGetValidationErrorInfo(req, new ValidationException(), errorMessages, false);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public ErrorInfo jsonNotReadableError(HttpServletRequest req, HttpMessageNotReadableException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        LOG.error("Exception at request " + req.getRequestURL(), logException ? e : null);
        return new ErrorInfo(req.getRequestURL(), e);
    }

    /**
     * Specialized method to send multiple validation messages as list in JSON.
     */
    private ValidationErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, ValidationException e, List<String> errors, boolean logException) {
        LOG.error("Validation exception at request " + req.getRequestURL(), logException ? e : null);
        return new ValidationErrorInfo(req.getRequestURL(), e, errors);
    }
}
