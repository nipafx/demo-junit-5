package org.codefx.demo.junit5.basics;

import org.junit.gen5.api.Test;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertNotSame;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.expectThrows;
import static org.junit.gen5.api.Assertions.fail;

class Assert {

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
	void failTheTest() {
		fail("epicly");
	}

	@Test
	void assertAllProperties() {
		Address address = new Address("New City", "Some Street", "No");

		assertAll("address",
				() -> assertEquals("Neustadt", address.city),
				() -> assertEquals("Irgendeinestraße", address.street),
				() -> assertEquals("Nr", address.number)
		);
	}

	@Test
	void assertExceptions() {
		assertThrows(Exception.class, this::throwing);

		Exception exception = expectThrows(Exception.class, this::throwing);
		assertEquals("Because I can!", exception.getMessage());
	}

	private void throwing() throws Exception {
		throw new Exception("Because I can!");
	}

	private static class Address {

		public final String city;
		public final String street;
		public final String number;

		private Address(String city, String street, String number) {
			this.city = city;
			this.street = street;
			this.number = number;
		}
	}

}
