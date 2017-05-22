package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.RandomProvider;
import org.codefx.demo.junit5.RandomProvider.SeededRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RandomProvider.class)
class RandomParameterExtensionTest {

	@BeforeAll
	static void beforeAll_1(SeededRandom r) {
		System.out.println("Before all #1: " + r.seed());
	}

	@BeforeAll
	static void beforeAll_2(SeededRandom r) {
		System.out.println("Before all #2: " + r.seed());
	}

	@BeforeEach
	void beforeEach_1(SeededRandom r) {
		System.out.println("Before each #1: " + r.seed());
	}

	@BeforeEach
	void beforeEach_2(SeededRandom r) {
		System.out.println("Before each #2: " + r.seed());
	}

	@Test
	void test(SeededRandom r) {
		System.out.println("Test: " + r.seed());
	}

	@Test
	void failingTest(SeededRandom r) {
		System.out.println("Failing Test: " + r.seed());
		throw new IllegalArgumentException("FAIL!");
	}

	@AfterEach
	void afterEach_1(SeededRandom r) {
		System.out.println("After each #1: " + r.seed());
	}

	@AfterEach
	void afterEach_2(SeededRandom r) {
		System.out.println("After each #2: " + r.seed());
	}

	@AfterAll
	static void afterAll_1(SeededRandom r) {
		System.out.println("After all #1: " + r.seed());
	}

	@AfterAll
	static void afterAll_2(SeededRandom r) {
		System.out.println("After all #2: " + r.seed());
	}

}
