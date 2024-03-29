package com.mscharhag.et.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mscharhag.et.TargetExceptionResolver;
import com.mscharhag.et.TranslationException;
import com.mscharhag.et.test.TestUtil;
import com.mscharhag.et.test.exceptions.BarRuntimeException;
import com.mscharhag.et.test.exceptions.FooRuntimeException;

public class ExceptionMappingsTest {

    ExceptionMappings exceptionMappings = new ExceptionMappings(null);


    @Test
    public void parentMappingsConvertExceptionToRuntimeException() {
        Exception ex = new Exception("foo");
        TestUtil.expectException(getTargetException(this.exceptionMappings, ex), RuntimeException.class, "foo", ex);
    }


    @Test
    public void parentMappingsDoNotConvertRuntimeException() {
        RuntimeException ex = new RuntimeException("foo");
        assertEquals(ex, getTargetException(this.exceptionMappings, ex));
    }


    @Test
    public void itIsPossibleToOverrideDefaultMappings() {
        Exception ex = new Exception("foo");
        this.exceptionMappings.addExceptionMapping(Exception.class, new ReflectiveExceptionResolver(FooRuntimeException.class));
        TestUtil.expectException(getTargetException(this.exceptionMappings, ex), FooRuntimeException.class, "foo", ex);
    }


    @Test(expected = TranslationException.class)
    public void itIsNotPossibleToAddDuplicateSourceExceptionMappings() {
        this.exceptionMappings.addExceptionMapping(Exception.class, new ReflectiveExceptionResolver(FooRuntimeException.class));
        this.exceptionMappings.addExceptionMapping(Exception.class, new ReflectiveExceptionResolver(BarRuntimeException.class));
    }

    @Test(expected = TranslationException.class)
    public void itIsPossibleToGetATranslationExceptionFromNoResolver() {
      exceptionMappings.parentExceptionMappings = null;
      Exception ex = new Exception("foo");
      exceptionMappings.getExceptionResolver(Exception.class);
    }


    private RuntimeException getTargetException(ExceptionMappings mappings, Exception source) {
        TargetExceptionResolver exceptionResolver = mappings.getExceptionResolver(source.getClass());
        return exceptionResolver.getTargetException(source.getMessage(), source);
    }
}
