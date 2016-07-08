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

		/*
		 * TODO find out why the extension fails.
		 * Looks like `BenchmarkExtension.writeCurrentTime` and `BenchmarkExtension.readCurrentTime` get called twice
		 * for one test method but the first read access removes the launch time so the second fails.
		 */

		Thread.sleep(100);
		assertTrue(true);
	}

}
