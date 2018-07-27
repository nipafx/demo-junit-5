package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class OsCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	private ConditionEvaluationResult evaluateIfAnnotated(Optional<AnnotatedElement> element) {
		Optional<DisabledOnOs> disabled = element.flatMap(el -> findAnnotation(el, DisabledOnOs.class));
		if (disabled.isPresent())
			return disabledIfOn(disabled.get().value());

		Optional<TestExceptOnOs> testExcept = element.flatMap(el -> findAnnotation(el, TestExceptOnOs.class));
		if (testExcept.isPresent())
			return disabledIfOn(testExcept.get().value());

		return ConditionEvaluationResult.enabled("");
	}

	private ConditionEvaluationResult disabledIfOn(OS[] disabledOnOs) {
		OS os = OS.determine();
		if (Arrays.asList(disabledOnOs).contains(os))
			return ConditionEvaluationResult.disabled("Test is disabled on " + os + ".");
		else
			return ConditionEvaluationResult.enabled("Test is not disabled in " + os + ".");
	}

}
