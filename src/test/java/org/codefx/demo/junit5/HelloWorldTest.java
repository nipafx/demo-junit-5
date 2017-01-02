package org.codefx.demo.junit5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Typical "Hello World"; also shows that Mockito and AssertJ are compatible.
 */
class HelloWorldTest {

	@Test
	void helloJUnit5() {
		System.out.println("Hello, JUnit 5.");
	}

	@Test
	void usingOtherLibs() {
		List mockedList = when(
				mock(List.class)
						.isEmpty())
				.thenReturn(true)
				.getMock();
		// passes because we just mocked 'mockedList.isEmpty' to return true
		assertThat(mockedList).isEmpty();
	}

}
