package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExtensionContext;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RandomProvider implements ParameterResolver, TestExecutionExceptionHandler {

	private static final Namespace NAMESPACE = Namespace.create("org", "codefx", "RandomProvider");

	@Override
	public boolean supports(
			ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> targetType = parameterContext.getParameter().getType();
		return targetType == Random.class || targetType == SeededRandom.class;
	}

	@Override
	public Object resolve(
			ParameterContext parameterContext, ExtensionContext context)
			throws ParameterResolutionException {
		return randomByUniqueId(context)
				.computeIfAbsent(context.getUniqueId(), SeededRandom::create);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, SeededRandom> randomByUniqueId(ExtensionContext context) {
		return context
				.getStore(NAMESPACE)
				.getOrComputeIfAbsent("Map<Unique ID, SeededRandom>", key -> new ConcurrentHashMap<>(), Map.class);
	}

	@Override
	public void handleTestExecutionException(
			TestExtensionContext context, Throwable throwable) throws Throwable {
		String seed = Optional.ofNullable(randomByUniqueId(context).get(context.getUniqueId()))
				.map(SeededRandom::seed)
				.map(s -> "seed " + s)
				.orElse("unknown seed");
		System.out.println("Exception occurred in test based on " + seed);
	}

	public static class SeededRandom extends Random {

		private static Random seeder = new Random();

		private final String testId;
		private final long seed;

		private SeededRandom(String testId, long seed) {
			super(seed);
			this.testId = testId;
			this.seed = seed;
		}

		static SeededRandom create(String testId) {
			return new SeededRandom(testId, seeder.nextLong());
		}

		public String testId() {
			return testId;
		}

		public long seed() {
			return seed;
		}

	}

}
