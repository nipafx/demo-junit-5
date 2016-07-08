package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestExtensionContext;

import static java.lang.System.currentTimeMillis;

public class BenchmarkExtension
		implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

	private static final Namespace NAMESPACE = Namespace.create("BenchmarkExtension");

	@Override
	public void beforeAll(ContainerExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		writeCurrentTime(context, LaunchTimeKey.CLASS);
	}

	@Override
	public void beforeEach(TestExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		writeCurrentTime(context, LaunchTimeKey.TEST);
	}

	@Override
	public void afterEach(TestExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		long launchTime = loadLaunchTime(context, LaunchTimeKey.TEST);
		long runtime = currentTimeMillis() - launchTime;
		print("Test", context.getDisplayName(), runtime);
	}

	@Override
	public void afterAll(ContainerExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		long launchTime = loadLaunchTime(context, LaunchTimeKey.CLASS);
		long runtime = currentTimeMillis() - launchTime;
		print("Test container", context.getDisplayName(), runtime);
	}

	private static boolean shouldBeBenchmarked(ExtensionContext context) {
		return context.getElement()
				.map(el -> el.isAnnotationPresent(Benchmark.class))
				.orElse(false);
	}

	private static void writeCurrentTime(ExtensionContext context, LaunchTimeKey key) {
		context.getStore(NAMESPACE).put(key, currentTimeMillis());
	}

	private static long loadLaunchTime(ExtensionContext context, LaunchTimeKey key) {
		return context.getStore(NAMESPACE).remove(key, long.class);
	}

	private static void print(String unit, String displayName, long runtime) {
		System.out.printf("%s '%s' took %d ms.%n", unit, displayName, runtime);
	}

	private enum LaunchTimeKey {
		CLASS, TEST
	}
}
