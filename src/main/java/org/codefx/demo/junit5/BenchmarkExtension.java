package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import static java.lang.System.currentTimeMillis;
import static java.util.Collections.singletonMap;
import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

class BenchmarkExtension
		implements BeforeAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterAllCallback {

	private static final Namespace NAMESPACE = Namespace.create("org", "codefx", "BenchmarkExtension");

	// EXTENSION POINTS

	@Override
	public void beforeAll(ExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		storeNowAsLaunchTime(context, LaunchTimeKey.CLASS);
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		storeNowAsLaunchTime(context, LaunchTimeKey.TEST);
	}

	@Override
	public void afterTestExecution(ExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		long launchTime = loadLaunchTime(context, LaunchTimeKey.TEST);
		long runtime = currentTimeMillis() - launchTime;
		report("Test", context, runtime);
	}

	@Override
	public void afterAll(ExtensionContext context) {
		if (!shouldBeBenchmarked(context))
			return;

		long launchTime = loadLaunchTime(context, LaunchTimeKey.CLASS);
		long elapsedTime = currentTimeMillis() - launchTime;
		report("Test container", context, elapsedTime);
	}

	// HELPER

	private static boolean shouldBeBenchmarked(ExtensionContext context) {
		return context.getElement()
				.map(el -> isAnnotated(el, Benchmark.class))
				.orElse(false);
	}

	private static void storeNowAsLaunchTime(ExtensionContext context, LaunchTimeKey key) {
		context.getStore(NAMESPACE).put(key, currentTimeMillis());
	}

	private static long loadLaunchTime(ExtensionContext context, LaunchTimeKey key) {
		return context.getStore(NAMESPACE).get(key, long.class);
	}

	private static void report(String unit, ExtensionContext context, long elapsedTime) {
		String message = String.format("%s '%s' took %d ms.", unit, context.getDisplayName(), elapsedTime);
		context.publishReportEntry(singletonMap("Benchmark", message));
	}

	private enum LaunchTimeKey {
		CLASS, TEST
	}

}
