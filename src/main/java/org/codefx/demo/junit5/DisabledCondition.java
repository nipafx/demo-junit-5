package org.codefx.demo.junit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ContainerExecutionCondition;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionCondition;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

/**
 * An exemplary reimplementation of the extension supporting the {@link Disabled @Disabled} annotation.
 */
public class DisabledCondition implements ContainerExecutionCondition, TestExecutionCondition {

	private static final ConditionEvaluationResult ENABLED =
			ConditionEvaluationResult.enabled("@Disabled is not present");

	@Override
	public ConditionEvaluationResult evaluate(ContainerExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	@Override
	public ConditionEvaluationResult evaluate(TestExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	private ConditionEvaluationResult evaluateIfAnnotated(Optional<AnnotatedElement> element) {
		return element.filter(el -> AnnotationUtils.isAnnotated(el, Disabled.class))
				.map(el -> ConditionEvaluationResult.disabled(el + " is @Disabled"))
				.orElse(ENABLED);
	}

}
