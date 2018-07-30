package org.codefx.demo.junit5.nested;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NestTest {
	
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
