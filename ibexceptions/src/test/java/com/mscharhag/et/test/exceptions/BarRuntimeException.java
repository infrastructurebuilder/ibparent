package com.mscharhag.et.test.exceptions;

@SuppressWarnings("serial")
public class BarRuntimeException extends RuntimeException {

    public BarRuntimeException(String message) {
        super(message);
    }

    public BarRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
