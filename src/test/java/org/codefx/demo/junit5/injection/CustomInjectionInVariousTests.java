package org.codefx.demo.junit5.injection;

import org.codefx.demo.junit5.RandomIntegerResolver;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class CustomInjectionInVariousTests {

	@Test
	@ExtendWith(RandomIntegerResolver.class)
	void regular(int randomized) {
		System.out.println("Random integer: " + randomized);
	}

	@TestFactory
	@ExtendWith(RandomIntegerResolver.class)
	Stream<DynamicTest> dynamic(int randomized) {
		return Stream.of(
				dynamicTest("#1", () -> System.out.println("Random integer: " + randomized)),
				dynamicTest("#2", () -> System.out.println("Random integer: " + randomized))
		);
	}

	@ParameterizedTest
	@MethodSource
	@ExtendWith(RandomIntegerResolver.class)
	void parameterized(String param, int randomized) {
		System.out.println("Random integer: " + randomized);
		assertTrue(true);
	}

	private static Stream<String> parameterized() {
		return Stream.of("first", "second");
	}

	@RepeatedTest(5)
	@ExtendWith(RandomIntegerResolver.class)
	void repeated(int randomized) {
		System.out.println("Random integer: " + randomized);
		assertTrue(true);
	}

}
