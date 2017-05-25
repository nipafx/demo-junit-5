package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MetaAnnotationTest {

	@Params
	void testMetaAnnotation(String s, int i) {
		System.out.println(s + ": " + i);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@ParameterizedTest(name = "Elaborate name listing all {arguments}")
	@CsvSource({ "Foo, 1", "Bar, 2" })
	@interface Params {

	}

}
