package org.codefx.demo.junit5.dynamic;

import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class DynamicContainerTest {

	@TestFactory
	List<DynamicContainer> registeredTests() {
		return asList(
				dynamicContainer(
						"Dynamic Container #1",
						asList(
								dynamicTest(
										"Dynamic Test #1",
										() -> System.out.println("Hi, this is Dynamic Test #1!")),
								dynamicTest(
										"Dynamic Test #2",
										() -> System.out.println("Hi, this is Dynamic Test #2!")))
				),
				dynamicContainer(
						"Dynamic Container #2",
						asList(
								dynamicTest(
										"Dynamic Test #A",
										() -> System.out.println("Hi, this is Dynamic Test #A!")),
								dynamicTest(
										"Dynamic Test #B",
										() -> System.out.println("Hi, this is Dynamic Test #B!")))
				)
		);
	}

}
