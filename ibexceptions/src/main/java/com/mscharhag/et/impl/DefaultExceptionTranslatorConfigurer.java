package com.mscharhag.et.impl;

import java.util.List;

import com.mscharhag.et.ExceptionMappingConfigurer;
import com.mscharhag.et.ExceptionTranslator;
import com.mscharhag.et.ExceptionTranslatorConfigurer;

public class DefaultExceptionTranslatorConfigurer extends ExceptionTranslatorConfigurer {

    protected ExceptionMappings mappings;

    public DefaultExceptionTranslatorConfigurer() {
        this(null);
    }

    DefaultExceptionTranslatorConfigurer(ExceptionMappings mappings) {
        this.mappings = mappings;
        if (mappings == null) {
            this.mappings = new ExceptionMappings(null);
        }
    }



    @Override
    protected ExceptionMappingConfigurer translate(List<Class<? extends Exception>> sources) {
        return new DefaultExceptionMappingConfigurer(this.mappings, sources);
    }

    @Override
    public ExceptionTranslator done() {
        return new DefaultExceptionTranslator(mappings);
    }

}
