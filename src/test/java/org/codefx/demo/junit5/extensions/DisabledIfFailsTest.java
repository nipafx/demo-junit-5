package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.CollectExceptions;
import org.codefx.demo.junit5.DisabledIfTestFailedWith;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@CollectExceptions
@DisabledIfTestFailedWith(RuntimeException.class)
class DisabledIfFailsTest {

	private static boolean failedFirst = false;

	@Test
	void a_throwException() {
		System.out.println("I failed!");
		failedFirst = true;
		throw new RuntimeException();
	}

	@Test
	void b_disableIfOtherFailedFirst() {

		/*
		 * TODO it looks like this does not work either.
		 */

		System.out.println("Nobody failed yet! (Right?)");
		assertFalse(failedFirst);
	}

}
