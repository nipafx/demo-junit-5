package org.codefx.demo.junit5.dynamic;

import org.codefx.demo.junit5.dynamic.ArithmeticNode.ArithmeticOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.codefx.demo.junit5.dynamic.ArithmeticNode.ArithmeticOperator.ADD;
import static org.codefx.demo.junit5.dynamic.ArithmeticNode.ArithmeticOperator.MULTIPLY;
import static org.codefx.demo.junit5.dynamic.ArithmeticNode.operationFor;
import static org.codefx.demo.junit5.dynamic.ArithmeticNode.valueOf;

/**
 * A test helper that generates an arithmetic tree together with the correct solutions.
 */
class ArithmeticTreeTestData {

	private final ArithmeticNode tree;
	private final Map<ArithmeticNode, Long> results;

	private ArithmeticTreeTestData(ArithmeticNode tree, Map<ArithmeticNode, Long> results) {
		this.tree = tree;
		this.results = results;
	}

	static ArithmeticTreeTestData generate() {
		var _1 = valueOf(1);
		var _2 = valueOf(2);
		var _3 = valueOf(3);
		var _4 = valueOf(4);
		var _5 = valueOf(5);
		var add_1_3_2 = operationFor(ADD, _1, _3, _2);
		var add_2_5 = operationFor(ADD, _2, _5);
		var multiply_6_7 = operationFor(MULTIPLY, add_1_3_2, add_2_5);
		var add_4_42 = operationFor(ADD, _4, multiply_6_7);
		var results = Map.of(
				_1, 1L, _2, 2L, _3, 3L, _4, 4L, _5, 5L,
				add_1_3_2, 6L,
				add_2_5, 7L,
				multiply_6_7, 42L,
				add_4_42, 46L);
		return new ArithmeticTreeTestData(add_4_42, results);
	}

	static ArithmeticTreeTestData generateRandom() {
		var random = new Random();
		var results = new HashMap<ArithmeticNode, Long>();

		for (int i = 0; i < 5; i++) {
			long number = random.nextInt(100);
			results.put(valueOf(number), number);
		}

		ArithmeticNode topNode = null;
		for (int i = 0; i < 5; i++) {
			var operatorIndex = random.nextInt(ArithmeticOperator.values().length);
			var operator = ArithmeticOperator.values()[operatorIndex];
			var nodes = new ArrayList<>(results.keySet());
			var operands = pickRandomNodes(nodes, random);
			var result = determineResult(operator, operands, results);
			topNode = operationFor(operator, operands);
			results.put(topNode, result);
		}

		return new ArithmeticTreeTestData(topNode, results);
	}

	private static ArithmeticNode[] pickRandomNodes(List<ArithmeticNode> nodes, Random random) {


		return IntStream.range(0, random.nextInt(3) + 2)
				// compute a random index
				.map(n -> random.nextInt(nodes.size()))
				.mapToObj(nodes::get)
				.toArray(ArithmeticNode[]::new);
	}

	private static long determineResult(
			ArithmeticOperator operator,
			ArithmeticNode[] operands,
			HashMap<ArithmeticNode, Long> results) {
		switch (operator) {
			case MULTIPLY:
				return Stream.of(operands)
						.mapToLong(results::get)
						.reduce(1, (o1, o2) -> o1 * o2);
			case ADD:
				return Stream.of(operands)
						.mapToLong(results::get)
						.sum();
			default: throw new IllegalStateException();
		}
	}

	ArithmeticNode tree() {
		return tree;
	}

	long resultFor(ArithmeticNode node) {
		return Optional.of(results.get(node)).orElseThrow(() -> new IllegalArgumentException("No result for " + node));
	}
}
