package org.codefx.demo.junit5.dynamic;

import org.codefx.demo.junit5.LambdaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LambdaTestTest extends LambdaTest {{

	/*
	 * NOTE: Using the lambda parameter's name as a test name no longer works on Java 9+
	 *       (see https://bugs.openjdk.java.net/browse/JDK-8138729).
	 */

	λ("My first lambda test", () -> {
		System.out.println("Hi, this is Lambda Test #1");
		assertTrue(true);
	});

	λ(my_second_lambda_test_fails -> {
		System.out.println("Hi, this is Lambda Test #2");
		assertTrue(false);
	});

}}
