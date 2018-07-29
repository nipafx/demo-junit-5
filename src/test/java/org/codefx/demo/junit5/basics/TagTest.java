package org.codefx.demo.junit5.basics;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class TagTest {

	@Test
	@Tag("database")
	void database() {
		fail("This test should have been filtered and not executed!");
	}

}
