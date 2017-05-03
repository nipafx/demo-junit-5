package org.codefx.demo.junit5.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public interface Interface {

	@Test
	default void testFails() {
		assertFalse(true);
	}

}
