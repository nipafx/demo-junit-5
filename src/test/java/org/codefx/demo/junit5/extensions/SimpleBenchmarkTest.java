package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.SimpleBenchmark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SimpleBenchmark
class SimpleBenchmarkTest {

	@Test
	void benchmarked() {
		assertTrue(true);
	}

	@Test
	@SimpleBenchmark
	void benchmarkedTwice() throws InterruptedException {
		Thread.sleep(100);
		assertTrue(true);
	}

}
