package com.isossoma.quotation.domain.exception;

public class InvalidQuotationException extends RuntimeException {
    public InvalidQuotationException(String message) {
        super(message);
    }
}