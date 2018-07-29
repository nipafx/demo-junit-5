package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

public class RandomIntegerResolver implements ParameterResolver {

	private static final Random RANDOM = new Random();

	@Override
	public boolean supportsParameter(
			ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		// don't blindly support a common type like `Integer`
		// instead it should be annotated with `@Randomized` or something
		Class<?> targetType = parameterContext.getParameter().getType();
		return targetType == Integer.class || targetType == int.class;
	}

	@Override
	public Object resolveParameter(
			ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return RANDOM.nextInt();
	}
}
