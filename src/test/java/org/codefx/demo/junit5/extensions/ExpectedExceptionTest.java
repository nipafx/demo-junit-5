package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Test;

class ExpectedExceptionTest {

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
