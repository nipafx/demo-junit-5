package org.codefx.demo.junit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

/**
 * An exemplary reimplementation of the extension supporting the {@link Disabled @Disabled} annotation.
 */
public class DisabledCondition implements ExecutionCondition {

	private static final ConditionEvaluationResult ENABLED =
			ConditionEvaluationResult.enabled("@Disabled is not present");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		return evaluateIfAnnotated(context.getElement());
	}

	private ConditionEvaluationResult evaluateIfAnnotated(Optional<AnnotatedElement> element) {
		return element.filter(el -> isAnnotated(el, Disabled.class))
				.map(el -> ConditionEvaluationResult.disabled(el + " is @Disabled"))
				.orElse(ENABLED);
	}

}
