package org.codefx.demo.junit5.basics;// NOT_PUBLISHED

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

class Nest {
	
	int count = Integer.MIN_VALUE;
	
	@BeforeEach
	void setCountToZero() {
		count = 0;
	}
	
	@Test
	void countIsZero() {
		assertEquals(0, count);
	}
	
	@Nested
	class CountGreaterZero {

		@BeforeEach
		void increaseCount() {
			count++;
		}

		@Test
		void countIsGreaterZero() {
			assertTrue(count > 0);
		}

		@Nested
		class CountMuchGreaterZero {

			@BeforeEach
			void increaseCount() {
				count += Integer.MAX_VALUE / 2;
			}

			@Test
			void countIsLarge() {
				assertTrue(count > Integer.MAX_VALUE / 2);
			}

		}

	}
	
}
