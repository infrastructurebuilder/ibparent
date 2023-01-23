package com.mscharhag.et.test.exceptions;

@SuppressWarnings("serial")
public class BarException extends Exception {

    public BarException(String message) {
        super(message);
    }

    public BarException(String message, Throwable cause) {
        super(message, cause);
    }
}
