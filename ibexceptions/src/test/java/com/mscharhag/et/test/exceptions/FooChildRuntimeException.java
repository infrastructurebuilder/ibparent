package com.mscharhag.et.test.exceptions;

@SuppressWarnings("serial")
public class FooChildRuntimeException extends FooRuntimeException {

    public FooChildRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FooChildRuntimeException(String message) {
        super(message);
    }
}
