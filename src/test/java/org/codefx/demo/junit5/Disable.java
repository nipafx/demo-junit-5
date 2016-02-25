package org.codefx.demo.junit5;

import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertTrue;

class Disable {

	@Test
	@Disabled("Y U No Pass?!")
	void failingTest() {
		assertTrue(false);
	}

}
