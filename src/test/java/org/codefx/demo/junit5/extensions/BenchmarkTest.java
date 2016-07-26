package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Benchmark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Benchmark
class BenchmarkTest {

	@Test
	void notBenchmarked() {
		assertTrue(true);
	}

	@Test
	@Benchmark
	void benchmarked() throws InterruptedException {
		Thread.sleep(100);
		assertTrue(true);
	}

}
