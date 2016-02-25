package org.codefx.demo.junit5;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assumptions.assumeTrue;

class Lifecycle {

	boolean value = true;

	@BeforeAll
	static void initializeExternalResources() {
		System.out.println("Initializing external resources...");
	}

	@BeforeEach
	void initializeMockObjects() {
		System.out.println("Initializing mock objects...");
	}

	@Test
	void someTest() {
		System.out.println("Running some test...");
		assertTrue(value);
	}

	@Test
	void otherTest() {
		assumeTrue(value);

		System.out.println("Running another test...");
		assertNotEquals(1, 42, "Why wouldn't these be the same?");
	}

	@AfterEach
	void tearDown() {
		System.out.println("Tearing down...");
	}

	@AfterAll
	static void freeExternalResources() {
		System.out.println("Freeing external resources...");
	}

}
