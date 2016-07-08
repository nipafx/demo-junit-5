package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionCondition;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Arrays;
import java.util.Optional;

public class DisabledIfTestFailedCondition implements TestExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluate(TestExtensionContext context) {
		Optional<DisabledIfTestFailedWith> disabled =
				AnnotationUtils.findAnnotation(context.getElement(), DisabledIfTestFailedWith.class);
		if (disabled.isPresent())
			return disableIfExceptionWasThrown(context, disabled.get().value());

		return ConditionEvaluationResult.enabled("");
	}

	private ConditionEvaluationResult disableIfExceptionWasThrown(
			ExtensionContext context,
			Class<? extends Exception>[] exceptions) {
		return Arrays.stream(exceptions)
				.filter(ex -> wasThrown(context, ex))
				.findAny()
				.map(thrown -> ConditionEvaluationResult.disabled(thrown.getSimpleName() + " was thrown."))
				.orElseGet(() -> ConditionEvaluationResult.enabled(""));
	}

	private static boolean wasThrown(ExtensionContext context, Class<? extends Exception> exception) {
		return CollectExceptionExtension.getThrownExceptions(context)
				.map(Object::getClass)
				.anyMatch(exception::isAssignableFrom);
	}
}
