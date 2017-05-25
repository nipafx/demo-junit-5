package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArgumentConverterTest {

	@ParameterizedTest
	@ValueSource(strings = { "x" })
	void testSimpleCharConversion(char c) {
		assertNotNull(c);
	}

	@ParameterizedTest
	@ValueSource(strings = { "☺️" })
	void testUtfCharConversion_fails(char c) {
		assertNotNull(c);
	}

	@ParameterizedTest
	@CsvSource({ "true, 3.14159265359, JUNE, 2017, 2017-06-21T22:00:00" })
	void testDefaultConverters(
			boolean b, double d, Summer s, Year y, LocalDateTime dt) {
	}

	enum Summer {
		JUNE, JULY, AUGUST, SEPTEMBER;
	}

}
