package org.codefx.demo.junit5;

import org.junit.gen5.api.extension.ConditionEvaluationResult;
import org.junit.gen5.api.extension.ContainerExecutionCondition;
import org.junit.gen5.api.extension.ContainerExtensionContext;
import org.junit.gen5.api.extension.TestExecutionCondition;
import org.junit.gen5.api.extension.TestExtensionContext;
import org.junit.gen5.commons.util.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Optional;

public class OsCondition implements ContainerExecutionCondition, TestExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluate(ContainerExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	@Override
	public ConditionEvaluationResult evaluate(TestExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	private ConditionEvaluationResult evaluateIfAnnotated(AnnotatedElement element) {
		Optional<DisabledOnOs> disabled = AnnotationUtils.findAnnotation(element, DisabledOnOs.class);
		if (disabled.isPresent())
			return disabledIfOn(disabled.get().value());

		Optional<TestExceptOnOs> testExcept = AnnotationUtils.findAnnotation(element, TestExceptOnOs.class);
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
