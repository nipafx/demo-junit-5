package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.DisabledIfTestFailedWith;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisabledIfTestFailedWith(RuntimeException.class)
class DisabledIfFailsTest {

	private static boolean ONE_TEST_FAILED = false;

	@Test
	void assertNoTestFailed_thenFail_1() {
		assertThenFail();
	}

	@Test
	void assertNoTestFailed_thenFail_2() {
		assertThenFail();
	}

	private void assertThenFail() {
		assertFalse(ONE_TEST_FAILED, "No test should run after another failed!");
		ONE_TEST_FAILED = true;
		throw new IndexOutOfBoundsException("I'm failing on purpose.");
	}

}
