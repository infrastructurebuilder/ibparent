package com.mscharhag.et;

import static com.mscharhag.et.test.TestUtil.BAR_EXCEPTION;
import static com.mscharhag.et.test.TestUtil.BAR_EXCEPTION_MESSAGE;
import static com.mscharhag.et.test.TestUtil.FOO_EXCEPTION;
import static com.mscharhag.et.test.TestUtil.FOO_EXCEPTION_MESSAGE;
import static com.mscharhag.et.test.TestUtil.expectException;
import static com.mscharhag.et.test.TestUtil.translateException;

import org.junit.Test;

import com.mscharhag.et.test.exceptions.BarException;
import com.mscharhag.et.test.exceptions.BarRuntimeException;
import com.mscharhag.et.test.exceptions.FooException;
import com.mscharhag.et.test.exceptions.FooRuntimeException;

public class InheritedConfigurationTest {

    private ExceptionTranslator baseEt = ET.newConfiguration()
            .translate(FooException.class).to(FooRuntimeException.class)
            .done();

    @Test
    public void baseConfigurationIsInherited() {
        ExceptionTranslator et = baseEt.newConfiguration()
                .translate(BarException.class).to(BarRuntimeException.class)
                .done();
        RuntimeException result = translateException(et, FOO_EXCEPTION);
        expectException(result, FooRuntimeException.class, FOO_EXCEPTION_MESSAGE, FOO_EXCEPTION);
    }


    @Test
    public void baseConfigurationCanBeOverridden() {
        ExceptionTranslator et = baseEt.newConfiguration()
                .translate(FooException.class).to(BarRuntimeException.class)
                .done();
        RuntimeException result = translateException(et, FOO_EXCEPTION);
        expectException(result, BarRuntimeException.class, FOO_EXCEPTION_MESSAGE, FOO_EXCEPTION);
    }

    @Test
    public void configurationCanBeAdded() {
        ExceptionTranslator et = baseEt.newConfiguration()
                .translate(BarException.class).to(BarRuntimeException.class)
                .done();
        RuntimeException result = translateException(et, BAR_EXCEPTION);
        expectException(result, BarRuntimeException.class, BAR_EXCEPTION_MESSAGE, BAR_EXCEPTION);
    }
}
