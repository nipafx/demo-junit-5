package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloParams {

	@ParameterizedTest
	void withoutSource_errors(String word) {
		assertNotNull(word);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Hello", "JUnit" })
	void withValueSource(String word) {
		assertNotNull(word);
	}

}
