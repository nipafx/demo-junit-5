package org.codefx.demo.junit5.basics;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class AssertTest {

	@Test
	void assertWithBoolean() {
		assertTrue(true);
		assertTrue(this::truism);

		assertFalse(false, () -> "Really " + "expensive " + "message" + ".");
	}

	private boolean truism() {
		return true;
	}

	@Test
	void assertWithComparison() {
		List<String> expected = asList("element");
		List<String> actual = new LinkedList<>(expected);

		assertEquals(expected, actual);
		assertEquals(expected, actual, "Different 'List' implementations should be equal.");
		assertEquals(expected, actual, () -> "Different " + "'List' implementations " + "should be equal.");

		assertNotSame(expected, actual, "Obviously not the same instance.");
	}

	@Test
	void failTheTest_fails() {
		fail("epicly");
	}

	@Test
	void assertAllProperties_fails() {
		Address address = new Address("New City", "Some Street", "No");

		assertAll("address",
				() -> assertEquals("Neustadt", address.city),
				() -> assertEquals("Irgendeinestraße", address.street),
				() -> assertEquals("Nr", address.number)
		);
	}

	static class Address {

		final String city;
		final String street;
		final String number;

		private Address(String city, String street, String number) {
			this.city = city;
			this.street = street;
			this.number = number;
		}
	}

	@Test
	void assertExceptions() {
		Exception exception = assertThrows(Exception.class, this::throwing);
		assertEquals("I'm failing on purpose.", exception.getMessage());
	}

	private void throwing() {
		throw new IndexOutOfBoundsException("I'm failing on purpose.");
	}

	@Test
	void assertTimeout_runsLate_failsButFinishes() {
		assertTimeout(of(100, MILLIS), () -> {
			sleepUninterrupted(250);
			// you will see this message
			System.out.println("Woke up");
		});
	}

	@Test
	void assertTimeoutPreemptively_runsLate_failsAndAborted() {
		assertTimeoutPreemptively(of(100, MILLIS), () -> {
			sleepUninterrupted(250);
			// you will NOT see this message
			System.out.println("Woke up");
		});
	}

	private static void sleepUninterrupted(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) { }
	}

}
