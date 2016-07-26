package org.codefx.demo.junit5.basics;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DisableTest {

	@Test
	@Disabled("Y U No Pass?!")
	void failingTest() {
		assertTrue(false);
	}

}
