package com.astrology.core;

public class AstrologyException extends RuntimeException {
    public static final String EPHEMERIS_ERROR = "EPHEMERIS_ERROR";
    public static final String INVALID_DATE = "INVALID_DATE";
    public static final String INVALID_COORDINATES = "INVALID_COORDINATES";
    public static final String CALCULATION_ERROR = "CALCULATION_ERROR";

    private final String errorCode;

    public AstrologyException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AstrologyException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
