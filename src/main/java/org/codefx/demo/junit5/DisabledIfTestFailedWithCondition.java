package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class DisabledIfTestFailedWithCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		Class<? extends Exception>[] exceptionTypes = context.getTestClass()
				.flatMap(testClass -> findAnnotation(testClass, DisabledIfTestFailedWith.class))
				.orElseThrow(() -> new IllegalStateException("The extension should not be executed "
						+ "unless the test class is annotated with @DisabledIfTestFailedWith."))
				.value();

		return disableIfExceptionWasThrown(context, exceptionTypes);
	}

	private ConditionEvaluationResult disableIfExceptionWasThrown(
			ExtensionContext context,
			Class<? extends Exception>[] exceptions) {
		return Arrays.stream(exceptions)
				.filter(ex -> wasThrown(context, ex))
				.findAny()
				.map(thrown -> disabled(thrown.getSimpleName() + " was thrown."))
				.orElseGet(() -> enabled(""));
	}

	private static boolean wasThrown(ExtensionContext context, Class<? extends Exception> exception) {
		return CollectExceptionExtension.getThrownExceptions(context)
				.map(Object::getClass)
				.anyMatch(exception::isAssignableFrom);
	}
}
