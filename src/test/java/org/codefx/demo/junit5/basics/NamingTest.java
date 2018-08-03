package org.codefx.demo.junit5.basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("What a nice name...")
class NamingTest {

	@Test
	@DisplayName("... for a test")
	void test() { }

}
