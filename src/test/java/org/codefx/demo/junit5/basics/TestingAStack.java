package org.codefx.demo.junit5.basics;// NOT_PUBLISHED

import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.expectThrows;

/**
 * Demonstration of JUnit 5 features (particularly nesting and naming)
 * that was taken from the <a href="http://junit-team.github.io/junit5/#nested-tests">JUnit 5 User Guide</a>. 
 */
@DisplayName("A stack")
class TestingAStack {

	Stack<Object> stack;
	boolean isRun = false;

	@Test
	@DisplayName("is instantiated with new Stack()")
	void isInstantiatedWithNew() {
		new Stack<Object>();
	}

	@Nested
	@DisplayName("when new")
	class WhenNew {

		@BeforeEach
		void init() {
			stack = new Stack<Object>();
		}

		@Test
		@DisplayName("is empty")
		void isEmpty() {
			assertTrue(stack.isEmpty());
		}

		@Test
		@DisplayName("throws EmptyStackException when popped")
		void throwsExceptionWhenPopped() {
			expectThrows(EmptyStackException.class, () -> stack.pop());
		}

		@Test
		@DisplayName("throws EmptyStackException when peeked")
		void throwsExceptionWhenPeeked() {
			expectThrows(EmptyStackException.class, () -> stack.peek());
		}

		@Nested
		@DisplayName("after pushing an element")
		class AfterPushing {

			String anElement = "an element";

			@BeforeEach
			void init() {
				stack.push(anElement);
			}

			@Test
			@DisplayName("it is no longer empty")
			void isEmpty() {
				assertFalse(stack.isEmpty());
			}

			@Test
			@DisplayName("returns the element when popped and is empty")
			void returnElementWhenPopped() {
				assertEquals(anElement, stack.pop());
				assertTrue(stack.isEmpty());
			}

			@Test
			@DisplayName("returns the element when peeked but remains not empty")
			void returnElementWhenPeeked() {
				assertEquals(anElement, stack.peek());
				assertFalse(stack.isEmpty());
			}
		}
	}
}