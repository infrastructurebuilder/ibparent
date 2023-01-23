package com.mscharhag.et.doc;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

import com.mscharhag.et.ET;
import com.mscharhag.et.ExceptionTranslator;
import com.mscharhag.et.test.TestUtil;

@SuppressWarnings("serial")
public class DocumentationExamplesTest {

    @Test
    public void motivation() {
        RuntimeException ex = TestUtil.catchException(() -> {
            /*
                Instead of:
                try {
                    // code that can throw SomeException
                    throw new SomeException();
                } catch (SomeException e) {
                    throw new SomeRuntimeException(e);
                }
            */

            // configure your mappings once
            ExceptionTranslator et = ET.newConfiguration()
                    .translate(SomeException.class).to(SomeRuntimeException.class)
                    .done();

            // will throw SomeRuntimeException
            et.translate(() -> {
                // code that can throw SomeException
                throw new SomeException();
            });
        });

        assertEquals(SomeRuntimeException.class, ex.getClass());
        assertEquals(SomeException.class, ex.getCause().getClass());
    }


    @Test
    public void gettingStartedtranslate()  {
        ExceptionTranslator et = ET.newConfiguration()
                .translate(ReflectiveOperationException.class).to(SomeRuntimeException.class)
                .done();

        et.translate(() -> {

            // this piece of code can throw NoSuchMethodException,
            // InvocationTargetException and IllegalAccessException

            // call String.toLowerCase() using reflection
            Method method = String.class.getMethod("toLowerCase");
            String result = (String) method.invoke("FOO");
        });

    }

    @Test
    public void gettingStartedreturns()  {
        ExceptionTranslator et = ET.newConfiguration()
                .translate(ReflectiveOperationException.class).to(SomeRuntimeException.class)
                .done();

        String result = et.returns(() -> {
            Method method = String.class.getMethod("toLowerCase");
            return (String) method.invoke("FOO");
        });

        assertEquals("foo", result);
    }



    public static class SomeException extends Exception {

    }

    public static class SomeRuntimeException extends RuntimeException {
        public SomeRuntimeException(Throwable cause) {
            super(cause);
        }
    }

}
