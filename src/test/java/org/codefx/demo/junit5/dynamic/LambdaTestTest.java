package org.codefx.demo.junit5.dynamic;

import org.codefx.demo.junit5.LambdaTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LambdaTestTest extends LambdaTest {

	LambdaTestTest() {
		λ(my_first_lambda_test -> {
			System.out.println("Hi, this is Lambda Test #1");
			assertTrue(true);
		});

		λ(my_second_lambda_test -> {
			System.out.println("Hi, this is Lambda Test #2");
			assertTrue(false);
		});
	}

	@Test
	void staticTest() {
		// see https://github.com/junit-team/junit5/issues/384
		assertTrue(true);
	}

}