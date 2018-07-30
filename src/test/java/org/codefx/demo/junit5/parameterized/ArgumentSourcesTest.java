package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArgumentSourcesTest {

	@ParameterizedTest
	@ValueSource(longs = { 42, 63 })
	void withValueSourceLong(long number) {
		assertNotNull(number);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Hello", "Parameterized" })
	void withOtherParams(String word, TestInfo info, TestReporter reporter) {
		reporter.publishEntry(info.getDisplayName(), "Word: " + word);
		assertNotNull(word);
	}

	@ParameterizedTest
	@EnumSource(TimeUnit.class)
	void withAllEnumValues(TimeUnit unit) {
		assertNotNull(unit);
	}

	@ParameterizedTest
	@EnumSource(TimeUnit.class)
	void withAllEnumValuesCrossProduct_errors(TimeUnit unit, TimeUnit unit2) {
		assertNotNull(unit);
	}

	@ParameterizedTest
	@EnumSource(value = TimeUnit.class, names = { "NANOSECONDS", "MICROSECONDS" })
	void withSomeEnumValues(TimeUnit unit) {
		assertNotNull(unit);
	}

	@ParameterizedTest
	@MethodSource("createWords")
	void withMethodSource(String word) {
		assertNotNull(word);
	}

	private static Stream<String> createWords() {
		return Stream.of("Hello", "Parameterized");
	}

	@ParameterizedTest
	@MethodSource("createWordsWithLength")
	void testWordLength(String word, int length) {
		assertEquals(length, word.length());
	}

	private static Stream<Arguments> createWordsWithLength() {
		return Stream.of(
				Arguments.of("Hello", 5),
				Arguments.of("Parameterized", 13));
	}

	@ParameterizedTest
	@CsvSource({ "Hello, 5", "Parameterized, 13", "'Hello, Parameterized!', 21" })
	void withCsvSource(String word, int length) {
		assertEquals(length, word.length());
	}

}
