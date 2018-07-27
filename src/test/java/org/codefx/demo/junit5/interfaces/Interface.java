package org.codefx.demo.junit5.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface Interface {

	@Test
	default void passes() {
		assertTrue(true);
	}

}
