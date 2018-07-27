package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeoutTest {

	@Test
	void quickTestWithoutTimeout_testPasses() {
		assertTrue(true);
	}

	@Test(timeout = 0)
	void quickTestWithoutZeroTimeout_testPasses() {
		assertTrue(true);
	}

	@Test(timeout = 10_000)
	void quickTestWithoutVeryLargeTimeout_testPasses() {
		assertTrue(true);
	}

	@Test(timeout = 10)
	void longRunningTest_fails() throws InterruptedException {
		Thread.sleep(100);
	}

}
