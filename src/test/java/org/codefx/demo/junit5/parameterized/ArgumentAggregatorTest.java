package org.codefx.demo.junit5.parameterized;

import org.codefx.demo.junit5.parameterized.CustomArgumentConverterTest.Point;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArgumentAggregatorTest {

	@ParameterizedTest
	@CsvSource({ "0, 0, 0", "1, 0, 1", "1, 1, 0", "1.414, 1, 1", "2.236, 2, 1" })
	void testPointNorm(double norm, ArgumentsAccessor arguments) {
		Point point = Point.from(arguments.getDouble(1), arguments.getDouble(2));
		assertEquals(norm, point.norm(), 0.01);
	}

	@ParameterizedTest
	@CsvSource({ "0, 0, 0", "1, 0, 1", "1, 1, 0", "1.414, 1, 1", "2.236, 2, 1" })
	// without ArgumentsAccessor in there, this leads to a ParameterResolutionException
	void testEatingArguments(double norm, ArgumentsAccessor arguments, TestReporter reporter) {
		reporter.publishEntry("norm", norm + "");
		assertThat(norm).isNotNegative();
	}

	@ParameterizedTest
	@CsvSource({ "0, 0, 0", "1, 0, 1", "1, 1, 0", "1.414, 1, 1", "2.236, 2, 1" })
	void testPointNorm(double norm, @AggregatePoint Point point) {
		assertEquals(norm, point.norm(), 0.01);
	}

	@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@AggregateWith(PointAggregator.class)
	@interface AggregatePoint { }

	static class PointAggregator implements ArgumentsAggregator {

		@Override
		public Object aggregateArguments(
				ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
			return Point.from(arguments.getDouble(1), arguments.getDouble(2));
		}

	}

	@ParameterizedTest
	@CsvSource({ "0" })
	void testNoFactoryAccess_errors(ArgumentsAccessor arguments) {
		// would be nice to pass NoFactoryConverter...
		NoFactory nope = arguments.get(0, NoFactory.class);
		assertNotNull(nope);
	}

	static class NoFactoryConverter implements ArgumentConverter {

		@Override
		public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
			if (source instanceof String)
				return new NoFactory(Integer.parseInt((String) source));
			throw new ArgumentConversionException(source + " could not be converted.");
		}
	}

	static class NoFactory {

		NoFactory(int __) { }

	}

}
