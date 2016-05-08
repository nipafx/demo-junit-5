package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.CollectExceptions;
import org.codefx.demo.junit5.DisabledIfTestFailedWith;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;

@RunWith(JUnit5.class)
@CollectExceptions
@DisabledIfTestFailedWith(RuntimeException.class)
public class DisabledIfFailsTest {

	private static boolean failedFirst = false;

	@Test
	void a_throwException() {
		System.out.println("I failed!");
		failedFirst = true;
		throw new RuntimeException();
	}

	@Test
	void b_disableIfOtherFailedFirst() {
		System.out.println("Nobody failed yet! (Right?)");
		assertFalse(failedFirst);
	}

}
