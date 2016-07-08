package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExtensionContext;

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
		ExtensionContext containerContext =
				getAncestorContainerContext(context)
						.orElseThrow(IllegalStateException::new);
		return (Set<Exception>) containerContext
				.getStore(NAMESPACE)
				.getOrComputeIfAbsent(THROWN_EXCEPTIONS_KEY, ignoredKey -> new HashSet<>());
	}

	private static Optional<ExtensionContext> getAncestorContainerContext(ExtensionContext context) {
		Optional<ExtensionContext> containerContext = Optional.of(context);
		while (containerContext.isPresent()
				&& !(containerContext.get() instanceof ContainerExtensionContext))
			containerContext = containerContext.get().getParent();
		return containerContext;
	}

	@Override
	public void handleTestExecutionException(TestExtensionContext context, Throwable throwable)
			throws Throwable {
		if (throwable instanceof Exception) {
			Exception exception = (Exception) throwable;
			getThrown(context).add(exception);
		}

		throw throwable;
	}
}
