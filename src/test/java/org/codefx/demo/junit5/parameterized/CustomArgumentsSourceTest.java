package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomArgumentsSourceTest {

	@ParameterizedTest
	@ArgumentsSource(RandomIntegerProvider.class)
	void testWithArgumentsSource(Integer argument) {
		assertNotNull(argument);
	}

	static class RandomIntegerProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return new Random()
					.ints(0, 10)
					.mapToObj(Arguments::of)
					.limit(3);
		}

	}

}
