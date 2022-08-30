package com.mscharhag.et.test.exceptions;

@SuppressWarnings("serial")
public class FooChildException extends FooException {

    public FooChildException(String message) {
        super(message);
    }

    public FooChildException(String message, Throwable cause) {
        super(message, cause);
    }
}
