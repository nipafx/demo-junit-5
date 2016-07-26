package org.codefx.demo.junit5.dynamic;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class DynamicTestTest {

	@TestFactory
	List<DynamicTest> registeredTests() {
		return asList(
				dynamicTest("Dynamic Test #1", () -> System.out.println("Hi, this is Dynamic Test #1!")),
				dynamicTest("Dynamic Test #2", () -> System.out.println("Hi, this is Dynamic Test #2!")));
	}

}