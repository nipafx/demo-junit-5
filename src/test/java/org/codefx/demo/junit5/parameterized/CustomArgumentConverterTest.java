package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomArgumentConverterTest {

	@ParameterizedTest
	@ValueSource(strings = { "(0/0)", "(0/1)", "(1/0)", "(1/1)", "(2/1)" })
	void testPointNotNull_1(@ConvertWith(PointConverter.class) Point point) {
		assertNotNull(point);
	}

	@ParameterizedTest
	@ValueSource(strings = { "(0/0)", "(0/1)", "(1/0)", "(1/1)", "(2/1)" })
	void testPointNotNull_2(@ConvertPoint Point point) {
		assertNotNull(point);
	}

	@ParameterizedTest
	@CsvSource({ "(0/0), 0", "(0/1), 1", "(1/0), 1", "(1/1), 1.414", "(2/1), 2.236" })
	void testPointNorm(@ConvertPoint Point point, double norm) {
		assertEquals(norm, point.norm(), 0.01);
	}

	@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@ConvertWith(PointConverter.class)
	@interface ConvertPoint { }

	static class PointConverter implements ArgumentConverter {

		@Override
		public Object convert(Object input, ParameterContext parameterContext) throws ArgumentConversionException {
			if (input instanceof Point)
				return input;
			if (input instanceof String)
				try {
					return Point.from((String) input);
				} catch (NumberFormatException ex) {
					String message = input + " is no correct string representation of a point.";
					throw new ArgumentConversionException(message, ex);
				}
			throw new ArgumentConversionException(input + " is no valid point");
		}

	}

	static class Point {

		final double x, y;

		private Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		static Point from(double x, double y) {
			return new Point(x, y);
		}

		static Point from(String x, String y) {
			return new Point(Double.parseDouble(x), Double.parseDouble(y));
		}

		static Point from(String xy) {
			if (!xy.startsWith("(") || !xy.endsWith(")"))
				throw new NumberFormatException(xy + " is no valid point");
			String[] x_y = xy.substring(1, xy.length() - 1).trim().split("/");
			if (x_y.length != 2)
				throw new NumberFormatException(xy + " is no valid point");
			return from(x_y[0].trim(), x_y[1].trim());
		}

		@Override
		public String toString() {
			return "(" + x + " / " + y + ")";
		}

		public double norm() {
			return Math.sqrt(x * x + y * y);
		}

	}

}
