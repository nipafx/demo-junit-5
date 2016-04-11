package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Benchmark;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import static org.junit.gen5.api.Assertions.assertTrue;

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
