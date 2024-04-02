package com.jpmc.collateral.exception;

/**
 * This exception can be used to represent and handle technical or infrastructure-related errors
 * in the application, where the errorCode can be used to provide more detailed information
 * about the error that occurred.
 */

public class TechnicalException extends RuntimeException {

    private final int errorCode;

    public TechnicalException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TechnicalException(int errorCode, String message, Throwable rootCause) {
        super(message, rootCause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
