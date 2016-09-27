package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Test;

import static org.junit.Assert.fail;

class ExpectedExceptionTest {

	@org.junit.jupiter.api.Test
	void __() {
		// this is only here so that IntelliJ understands it's a JUnit 5 test class
	}

	@Test
	void noExceptionExpected_throwsNoException_testPasses() {
		// do nothing
	}

	@Test
	void noExceptionExpected_throwsException_testFails() {
		throw new IllegalArgumentException();
	}

	@Test(expected = IllegalArgumentException.class)
	void exceptionExpected_throwsNoException_testFails() {
		// do nothing
	}

	@Test(expected = IllegalArgumentException.class)
	void exceptionExpected_throwsException_testPasses() {
		throw new IllegalArgumentException();
	}

}
