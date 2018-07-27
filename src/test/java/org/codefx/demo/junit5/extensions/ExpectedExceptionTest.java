package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.Test;

class ExpectedExceptionTest {

	@Test
	void noExceptionExpected_throwsNoException_passes() {
		// do nothing
	}

	@Test
	void noExceptionExpected_throwsException_fails() {
		throw new IndexOutOfBoundsException("I'm failing on purpose.");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	void exceptionExpected_throwsNoException_fails() {
		// do nothing
	}

	@Test(expected = IndexOutOfBoundsException.class)
	void exceptionExpected_throwsException_passes() {
		throw new IndexOutOfBoundsException("I'm failing on purpose.");
	}

}
