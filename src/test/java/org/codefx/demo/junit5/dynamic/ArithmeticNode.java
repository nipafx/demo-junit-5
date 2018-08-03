package org.codefx.demo.junit5.dynamic;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * A simplified version of a node in a tree that adds and multiplies longs.
 */
interface ArithmeticNode {

	long evaluate();

	List<ArithmeticNode> operands();

	static ArithmeticNode operationFor(ArithmeticOperator operator, ArithmeticNode... operands) {
		return new OperationNode(operator, operands);
	}

	static ArithmeticNode valueOf(long value) {
		return new ValueNode(value);
	}

	class OperationNode implements ArithmeticNode {

		private final ArithmeticOperator operator;

		private final ArithmeticNode[] operands;

		private OperationNode(ArithmeticOperator operator, ArithmeticNode[] operands) {
			this.operator = requireNonNull(operator);
			this.operands = requireNonNull(operands);
		}

		@Override
		public long evaluate() {
			long[] operandValues = Stream.of(operands)
					.mapToLong(ArithmeticNode::evaluate)
					.toArray();
			return operator.evaluate(operandValues);
		}

		@Override
		public List<ArithmeticNode> operands() {
			return List.of(operands);
		}

		@Override
		public String toString() {
			return operator.toString();
		}

	}

	class ValueNode implements ArithmeticNode {

		private final long value;

		private ValueNode(long value) {
			this.value = value;
		}

		@Override
		public long evaluate() {
			return value;
		}

		@Override
		public List<ArithmeticNode> operands() {
			return List.of();
		}

		@Override
		public String toString() {
			return "Value " + value;
		}

	}

	enum ArithmeticOperator {

		MULTIPLY(
				operands -> LongStream
						.of(operands)
						// implementation error to make tests interesting
						.map(operand -> operand % 10 == 0 ? operand / 10 : operand)
						.reduce(1, (o1, o2) -> o1 * o2),
				() -> "Multiplication"),

		ADD(
				operands -> LongStream
						.of(operands)
						// implementation error to make tests interesting
						.map(operand -> operand == 4 ? 3 : operand)
						.sum(),
				() -> "Addition");

		private final ToLongFunction<long[]> compute;
		private final Supplier<String> toString;

		ArithmeticOperator(ToLongFunction<long[]> compute, Supplier<String> toString) {
			this.compute = compute;
			this.toString = toString;
		}

		public long evaluate(long... operands) {
			return compute.applyAsLong(operands);
		}

		public String toString() {
			return toString.get();
		}
	}

}
