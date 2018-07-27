package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CollectExceptionExtension implements TestExecutionExceptionHandler {

	private static final Namespace NAMESPACE = Namespace.create("org", "codefx", "CollectExceptions");
	private static final String THROWN_EXCEPTIONS_KEY = "THROWN_EXCEPTIONS_KEY";

	public static Stream<Exception> getThrownExceptions(ExtensionContext context) {
		return getThrown(context).stream();
	}

	@SuppressWarnings("unchecked")
	private static Set<Exception> getThrown(ExtensionContext context) {
		return (Set<Exception>) context
				.getStore(NAMESPACE)
				.getOrComputeIfAbsent(THROWN_EXCEPTIONS_KEY, __ -> new HashSet<>());
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
			throws Throwable {
		if (throwable instanceof Exception) {
			Exception exception = (Exception) throwable;
			getThrown(context).add(exception);
		}

		throw throwable;
	}
}
