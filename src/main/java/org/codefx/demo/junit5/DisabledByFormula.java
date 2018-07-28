package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.function.BooleanSupplier;

public class DisabledByFormula implements ExecutionCondition {

	private final BooleanFormula formula;
	private final String message;

	private DisabledByFormula(String message, BooleanFormula formula) {
		this.formula = formula;
		this.message = message;
	}

	public static DisabledByFormula disabledWhen(String message, BooleanFormula formula) {
		return new DisabledByFormula(message, formula);
	}

	public static DisabledByFormula disabledWhen(String message, boolean value) {
		return new DisabledByFormula(message, () -> value);
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(
			ExtensionContext context) {
		return formula.evaluate()
				// disable when formula is true
				? ConditionEvaluationResult.disabled(message)
				: ConditionEvaluationResult.enabled("Not '" + message + "'");
	}

	@FunctionalInterface
	public interface BooleanFormula extends BooleanSupplier {

		@Override
		@Deprecated
		default boolean getAsBoolean() {
			return evaluate();
		}

		boolean evaluate();

		static BooleanFormula from(boolean value) {
			return () -> value;
		}

		static BooleanFormula from(BooleanSupplier term) {
			return term::getAsBoolean;
		}

		static BooleanFormula not(boolean value) {
			return () -> value;
		}

		static BooleanFormula not(BooleanSupplier term) {
			return () -> !term.getAsBoolean();
		}

		default BooleanFormula and(BooleanSupplier otherTerm) {
			return () -> this.evaluate() && otherTerm.getAsBoolean();
		}

		default BooleanFormula and(boolean value) {
			return () -> this.evaluate() && value;
		}

		default BooleanFormula or(BooleanSupplier otherTerm) {
			return () -> this.evaluate() || otherTerm.getAsBoolean();
		}

		default BooleanFormula or(boolean value) {
			return () -> this.evaluate() || value;
		}

	}

}
