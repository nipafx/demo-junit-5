package org.codefx.demo.junit5.injection;

import org.codefx.demo.junit5.RandomIntegerResolver;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;

class OrderTests {

	@Test
	@ExtendWith(RandomIntegerResolver.class)
	void customParameterFirst(int randomized, TestReporter reporter) {
		reporter.publishEntry("first parameter", "" + randomized);
	}

	@Test
	@ExtendWith(RandomIntegerResolver.class)
	void jupiterParameterFirst(TestReporter reporter, int randomized) {
		reporter.publishEntry("first parameter", "" + randomized);
	}

	@RepeatedTest(3)
	@ExtendWith(RandomIntegerResolver.class)
	void repetitionInfoFirst(RepetitionInfo info, TestReporter reporter, int randomized) {
		reporter.publishEntry("first parameter", "" + randomized);
	}

	@RepeatedTest(3)
	@ExtendWith(RandomIntegerResolver.class)
	void repetitionInfoLast(TestReporter reporter, int randomized, RepetitionInfo info) {
		reporter.publishEntry("first parameter", "" + randomized);
	}

}
