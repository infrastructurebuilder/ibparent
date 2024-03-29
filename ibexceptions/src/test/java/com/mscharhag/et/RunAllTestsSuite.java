package com.mscharhag.et;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mscharhag.et.doc.DocumentationExamplesTest;
import com.mscharhag.et.impl.ExceptionMappingsTest;
import com.mscharhag.et.impl.ReflectiveExceptionResolverTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DefaultConfigurationTest.class, CustomConfigurationTest.class, DocumentationExamplesTest.class,
        InheritedConfigurationTest.class, ReflectiveExceptionResolverTest.class, ExceptionMappingsTest.class })
public class RunAllTestsSuite {
}
