package org.codefx.demo.junit5.integrations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Point;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MockitoTest {

	@InjectMocks
	private Circle circle;

	@Mock
	private Point center;

	@Test
	void shouldInjectMocks() {
		assertThat(center).isNotNull();
		assertThat(circle).isNotNull();
		assertThat(circle.center()).isSameAs(center);
	}

	static class Circle {

		private final Point center;
		private final double radius;

		Circle(Point center) {
			this.center = center;
			this.radius = 1.0d;
		}

		Point center() {
			return center;
		}

		double radius() {
			return radius;
		}

		@Override
		public String toString() {
			return "Circle{" +
					"center=" + center +
					'}';
		}
	}

}
