package org.codefx.demo.junit5;

import org.junit.gen5.api.extension.AfterAllExtensionPoint;
import org.junit.gen5.api.extension.AfterEachExtensionPoint;
import org.junit.gen5.api.extension.BeforeAllExtensionPoint;
import org.junit.gen5.api.extension.BeforeEachExtensionPoint;
import org.junit.gen5.api.extension.ContainerExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.TestExtensionContext;

import static java.lang.System.currentTimeMillis;

public class BenchmarkExtension
		implements BeforeAllExtensionPoint, BeforeEachExtensionPoint, AfterEachExtensionPoint, AfterAllExtensionPoint {

	private static final Namespace NAMESPACE = Namespace.of("BenchmarkExtension");

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
		return context.getElement().isAnnotationPresent(Benchmark.class);
	}

	private static void writeCurrentTime(ExtensionContext context, LaunchTimeKey key) {
		context.getStore(NAMESPACE).put(key, currentTimeMillis());
	}

	private static long loadLaunchTime(ExtensionContext context, LaunchTimeKey key) {
		return (Long) context.getStore(NAMESPACE).remove(key);
	}

	private static void print(String unit, String displayName, long runtime) {
		System.out.printf("%s '%s' took %d ms.%n", unit, displayName, runtime);
	}

	private enum LaunchTimeKey {
		CLASS, TEST
	}
}
