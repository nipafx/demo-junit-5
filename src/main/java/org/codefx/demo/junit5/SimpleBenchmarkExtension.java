package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import static java.lang.System.currentTimeMillis;
import static java.util.Collections.singletonMap;

class SimpleBenchmarkExtension
		implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

	private static final Namespace NAMESPACE = Namespace.create("org", "codefx", "SimpleBenchmarkExtension");
	private static final String LAUNCH_TIME_KEY = "LaunchTime";

	@Override
	public void beforeTestExecution(ExtensionContext context) {
		storeNowAsLaunchTime(context);
	}

	@Override
	public void afterTestExecution(ExtensionContext context) {
		long launchTime = loadLaunchTime(context);
		long elapsedTime = currentTimeMillis() - launchTime;
		report(context, elapsedTime);
	}

	private static void storeNowAsLaunchTime(ExtensionContext context) {
		context.getStore(NAMESPACE).put(LAUNCH_TIME_KEY, currentTimeMillis());
	}

	private static long loadLaunchTime(ExtensionContext context) {
		return context.getStore(NAMESPACE).get(LAUNCH_TIME_KEY, long.class);
	}

	private static void report(ExtensionContext context, long elapsedTime) {
		String message = String.format("Test '%s' took %d ms.", context.getDisplayName(), elapsedTime);
		context.publishReportEntry(singletonMap("Benchmark", message));
	}

}
