package org.codefx.demo.junit5.basics;// NOT_PUBLISHED

import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assumptions.assumeFalse;
import static org.junit.gen5.api.Assumptions.assumeTrue;
import static org.junit.gen5.api.Assumptions.assumingThat;

class Assume {

	@Test
	void exitIfFalseIsTrue() {
		assumeTrue(false);
		System.exit(1);
	}
	
	@Test
	void exitIfTrueIsFalse() {
		assumeFalse(this::truism);
		System.exit(1);
	}
	
	private boolean truism() {
		return true;
	}
	
	@Test
	void exitIfNullEqualsString() {
		assumingThat(
				"null".equals(null),
				() -> System.exit(1)
		);
	}

}
