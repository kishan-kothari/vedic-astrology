package com.astrology.api.exception;

public class AstrologyException extends RuntimeException {
    public AstrologyException(String message) {
        super(message);
    }
    
    public AstrologyException(String message, Throwable cause) {
        super(message, cause);
    }
}
