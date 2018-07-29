package org.codefx.demo.junit5.scenario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class ClassTestInstanceTest {

	private static int CLASS_TEST_COUNT = 0;

	private int instanceTestCount = 0;

	@Test
	void testOnInstance_1() {
		incrementAndCompareTestCounts();
	}

	@Test
	void testOnInstance_2() {
		incrementAndCompareTestCounts();
	}

	@Test
	void testOnInstance_3() {
		incrementAndCompareTestCounts();
	}

	private void incrementAndCompareTestCounts() {
		CLASS_TEST_COUNT++;
		instanceTestCount++;
		// this assertion would fail if a new instance
		// would be created for each test method
		assertThat(instanceTestCount).isEqualTo(CLASS_TEST_COUNT);
	}

}
