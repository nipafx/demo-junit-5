package org.codefx.demo.junit5.parameterized;

import org.codefx.demo.junit5.parameterized.CustomArgumentConverterTest.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArgumentConverterTest {

	@ParameterizedTest
	@ValueSource(strings = { "x" })
	void testSimpleCharConversion(char c) {
		assertNotNull(c);
	}

	@ParameterizedTest
	@ValueSource(strings = { "☺️" })
	void testUtfCharConversion_errors(char c) {
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

	@ParameterizedTest
	@ValueSource(strings = { "x️" })
	void testTypeWithOneFactory(TypeWithOneFactory o) {
		assertNotNull(o);
	}

	static class TypeWithOneFactory {

		static TypeWithOneFactory of(String s) {
			return new TypeWithOneFactory();
		}

	}

	@ParameterizedTest
	@ValueSource(strings = { "x️" })
	void testTypeWithTwoFactories_constructorCalled(TypeWithTwoFactories o) {
		assertNotNull(o);
	}

	static class TypeWithTwoFactories {

		TypeWithTwoFactories(String s) {
			System.out.println("Constructor called with " + s);
		}

		static TypeWithTwoFactories of(String s) {
			System.out.println("Factory `of` called with " + s);
			return new TypeWithTwoFactories(s);
		}

		static TypeWithTwoFactories from(String s) {
			System.out.println("Factory `from` called with " + s);
			return new TypeWithTwoFactories(s);
		}

	}

	@ParameterizedTest
	@CsvSource({ "(0/0), 0", "(0/1), 1", "(1/0), 1", "(1/1), 1.414", "(2/1), 2.236" })
	// works because Point::from is suitable factory
	void testPointNorm(Point point, double norm) {
		assertEquals(norm, point.norm(), 0.01);
	}

}
