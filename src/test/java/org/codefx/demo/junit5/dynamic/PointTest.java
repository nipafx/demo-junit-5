package org.codefx.demo.junit5.dynamic;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

	/*
	 * NOTE: In essence, these are parameterized tests and outside of a demo
	 *       that feature should be used instead for such tests.
	 */

	void testDistanceComputation(Point p1, Point p2, double distance) {
		assertEquals(distance, p1.distanceTo(p2));
	}

	@Test
	void testDistanceComputations_loop() {
		List<PointPointDistance> testData = createTestData();
		for (PointPointDistance datum : testData) {
			testDistanceComputation(
					datum.point1(), datum.point2(), datum.distance());
		}
	}

	@TestFactory
	Stream<DynamicTest> testDistanceComputations_testFactory1() {
		List<PointPointDistance> testData = createTestData();
		return testData.stream()
				.map(datum -> DynamicTest.dynamicTest(
						"Testing " + datum,
						() -> testDistanceComputation(
								datum.point1(), datum.point2(), datum.distance()
						)));
	}

	@TestFactory
	Stream<DynamicTest> testDistanceComputations_testFactory2() {
		return DynamicTest.stream(
				createTestData().iterator(),
				datum -> "Testing " + datum,
				datum -> testDistanceComputation(
						datum.point1(), datum.point2(), datum.distance()));
	}

	// INNER CLASSES

	private List<PointPointDistance> createTestData() {
		return asList(
				new PointPointDistance(0, 0, 0, 0, 0), // pass
				new PointPointDistance(0, 0, 1, 1, 1), // fail
				new PointPointDistance(1, 2, 3, 4, 5), // fail
				new PointPointDistance(1, 2, 4, 6, 5)  // pass
		);
	}

	static class PointPointDistance {

		private final Point p1, p2;
		private final double distance;

		PointPointDistance(int x1, int y1, int x2, int y2, double distance) {
			this(new Point(x1, y1), new Point(x2, y2), distance);
		}

		PointPointDistance(Point p1, Point p2, double distance) {
			this.p1 = p1;
			this.p2 = p2;
			this.distance = distance;
		}

		Point point1() {
			return p1;
		}

		Point point2() {
			return p2;
		}

		double distance() {
			return distance;
		}

		@Override
		public String toString() {
			return "| " + p1 + " - " + p2 + " | = " + distance;
		}
	}

	static class Point {

		private final int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		double distanceTo(Point other) {
			return sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

}
