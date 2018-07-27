package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.AssertionFailedError;

import java.util.Optional;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class ExpectedExceptionExtension implements TestExecutionExceptionHandler, AfterTestExecutionCallback {

	/*
	 * This extension implements the exception handler callback to compare the thrown exception
	 * to what was expected. The after test callback (which is called later) builds on
	 * the results of that check and fails the test if an exception was expected but not thrown.
	 */

	private static final Namespace NAMESPACE = Namespace.create("org", "codefx", "ExpectedException");
	private static final String KEY = "ExceptionWasT";

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		boolean throwableMatchesExpectedException =
				expectedException(context)
						.filter(expected -> expected.isInstance(throwable))
						.isPresent();
		// in the `afterTestExecution` callback we have to pass or fail the test
		// depending on whether the exception was thrown or not;
		// to do that we need to register whether the exception was thrown;
		// (NOTE that if no exception was thrown, NOTHING is registered)
		if (throwableMatchesExpectedException)
			storeExceptionStatus(context, EXCEPTION.WAS_THROWN_AS_EXPECTED);
		else {
			// this extension is not in charge of the throwable, so we need to rethrow;
			storeExceptionStatus(context, EXCEPTION.WAS_THROWN_NOT_AS_EXPECTED);
			throw throwable;
		}
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		switch(loadExceptionStatus(context)) {
			case WAS_NOT_THROWN:
				expectedException(context)
						.map(expected -> new AssertionFailedError("Expected exception " + expected + " was not thrown.", expected, null))
						.ifPresent(ex -> { throw ex; });
			case WAS_THROWN_AS_EXPECTED:
				// the exception was thrown as expected so there is nothing to do
			case WAS_THROWN_NOT_AS_EXPECTED:
				// an exception was thrown but of the wrong type;
				// it was rethrown in `handleTestExecutionException`
				// so there is nothing to do here
		}
	}

	private static Optional<? extends Class<? extends Throwable>> expectedException(ExtensionContext context) {
		return context.getElement()
				.flatMap(el -> findAnnotation(el, Test.class))
				.map(Test::expected)
				.filter(exceptionType -> exceptionType != Test.None.class);
	}

	private static void storeExceptionStatus(ExtensionContext context, EXCEPTION thrown) {
		context.getStore(NAMESPACE).put(KEY, thrown);
	}

	private static EXCEPTION loadExceptionStatus(ExtensionContext context) {
		EXCEPTION thrown = context.getStore(NAMESPACE).get(KEY, EXCEPTION.class);
		if (thrown == null)
			return EXCEPTION.WAS_NOT_THROWN;
		else
			return thrown;
	}

	private enum EXCEPTION {
		WAS_NOT_THROWN,
		WAS_THROWN_AS_EXPECTED,
		WAS_THROWN_NOT_AS_EXPECTED,
	}

}
