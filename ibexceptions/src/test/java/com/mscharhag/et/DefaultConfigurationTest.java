package com.mscharhag.et;

import static com.mscharhag.oleaster.matcher.Matchers.expect;

import java.io.IOException;

import org.junit.Test;

import com.mscharhag.et.test.TestUtil;

public class DefaultConfigurationTest {

    private ExceptionTranslator et = ET.newConfiguration().done();
    private Exception checkedException = new IOException("checked exception", null);
    private RuntimeException runtimeException = new NullPointerException("runtime exception");


    @Test
    public void translateTranslatesCheckedExceptionsToRuntimeExceptions() {
        RuntimeException result = TestUtil.catchException(() -> {
            et.translate(() -> {
                throw checkedException;
            });
        });
        expect(result.getMessage()).toEqual("checked exception");
        expect(result.getCause()).toEqual(checkedException);
    }

    @Test
    public void translateDoesNothingIfNoExceptionIsThrown() {
        et.translate(() -> {
            // empty
        });
    }

    @Test
    public void translateDoesNotTranslateRuntimeExceptions() {
        RuntimeException result = TestUtil.catchException(() -> {
            et.translate(() -> {
                throw runtimeException;
            });
        });
        expect(result).toEqual(runtimeException);
    }

    @Test
    public void returnsTranslatesCheckedExceptionsToRuntimeExceptions() {
        RuntimeException result = TestUtil.catchException(() -> {
            et.returns(() -> {
                throw checkedException;
            });
        });
        expect(result.getMessage()).toEqual("checked exception");
        expect(result.getCause()).toEqual(checkedException);
    }

    @Test
    public void returnsReturnsTheValueIfNoExceptionIsThrown() {
        String result = et.returns(() -> "foo");
        expect(result).toEqual("foo");
    }

    @Test
    public void returnsDoesNotTranslateRuntimeExceptions() {
        RuntimeException result = TestUtil.catchException(() -> {
            et.returns(() -> {
                throw runtimeException;
            });
        });
        expect(result).toEqual(runtimeException);
    }

    @Test
    public void translateFailsIfNullIsPassed() {
        RuntimeException result = TestUtil.catchException(() -> et.translate(null));
        expect(result.getClass()).toEqual(IllegalArgumentException.class);
        expect(result.getMessage()).toEqual("null is not a valid argument for ET.translate()");
    }

    @Test
    public void returnsFailsIfNullIsPassed() {
        RuntimeException result = TestUtil.catchException(() -> et.returns(null));
        expect(result.getClass()).toEqual(IllegalArgumentException.class);
        expect(result.getMessage()).toEqual("null is not a valid argument for ET.returns()");
    }

}
