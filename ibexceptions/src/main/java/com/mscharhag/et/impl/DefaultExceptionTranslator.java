package com.mscharhag.et.impl;

import com.mscharhag.et.ExceptionTranslator;
import com.mscharhag.et.ExceptionTranslatorConfigurer;
import com.mscharhag.et.ReturningTryBlock;
import com.mscharhag.et.TargetExceptionResolver;
import com.mscharhag.et.TranslationException;
import com.mscharhag.et.TryBlock;

class DefaultExceptionTranslator implements ExceptionTranslator {

    protected ExceptionMappings exceptionMappings;

    DefaultExceptionTranslator(ExceptionMappings exceptionMappings) {
        this.exceptionMappings = exceptionMappings;
    }

    @Override
    public void translate(TryBlock tryBlock) {
        Arguments.ensureNotNull(tryBlock, "null is not a valid argument for ET.translate()");
        this.returns(() -> {
            tryBlock.run();
            return null;
        });
    }

    @Override
    public <T> T returns(ReturningTryBlock<T> invokable) {
        Arguments.ensureNotNull(invokable, "null is not a valid argument for ET.returns()");
        try {
            return invokable.run();
        } catch (Exception e) {
            throw this.getTargetException(e);
        }
    }

    @Override
    public ExceptionTranslatorConfigurer newConfiguration() {
        return new DefaultExceptionTranslatorConfigurer(new ExceptionMappings(this.exceptionMappings));
    }

    protected RuntimeException getTargetException(Exception source) {
        TargetExceptionResolver resolver = this.exceptionMappings.getExceptionResolver(source.getClass());
        RuntimeException targetException = resolver.getTargetException(source.getMessage(), source);
        if (targetException == null) {
            throw new TranslationException("TargetExceptionResolver returned null as target exception, " +
                    "targetExceptionResolver: " + resolver.getClass().getCanonicalName());
        }
        return targetException;
    }
}
