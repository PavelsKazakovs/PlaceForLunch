package com.pavel.placeforlunch.util.exception;

import javax.validation.ValidationException;
import java.util.List;

public class ValidationErrorInfo {
    public final String url;
    public final String cause;
    public final List<String> details;

    public ValidationErrorInfo(CharSequence url, ValidationException ex, List<String> errors) {
        this.url = url.toString();
        this.cause = ex.getClass().getSimpleName();
        this.details = errors;
    }
}
