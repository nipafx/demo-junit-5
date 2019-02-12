package org.codefx.demo.junit5.dynamic;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ArithmeticTreeTest {

	@TestFactory
	DynamicNode testArithmeticTree() {
		return generateTestPlan(ArithmeticTreeTestData.generate());
	}

	@TestFactory
	DynamicNode testRandomArithmeticTree() {
		return generateTestPlan(ArithmeticTreeTestData.generateRandom());
	}

	/*
	 * Below you find the generation of a test plan from the arithmetic tree.
	 */

	private DynamicNode generateTestPlan(ArithmeticTreeTestData treeTestData) {
		return generateTestTreeFor(treeTestData.tree(), treeTestData);
	}

	private static DynamicNode generateTestTreeFor(
			ArithmeticNode arithmeticNode, ArithmeticTreeTestData treeTestData) {
		var testForNode = generateTestFor(arithmeticNode, treeTestData);
		if (arithmeticNode.operands().isEmpty())
			return testForNode;
		else {
			var testsForChildren = generateTestsFor(arithmeticNode.operands(), treeTestData);
			var expected = treeTestData.resultFor(arithmeticNode);
			var testName = arithmeticNode + " should evaluate to " + expected + " (ops '+3' and '*10' fail)";
			return dynamicContainer(testName, concat(of(testForNode), testsForChildren));
		}
	}

	private static DynamicTest generateTestFor(
			ArithmeticNode arithmeticNode, ArithmeticTreeTestData treeTestData) {
		var expected = treeTestData.resultFor(arithmeticNode);
		var testName = arithmeticNode.operands().isEmpty()
				? arithmeticNode + " should evaluate to " + expected
				: arithmeticNode + " of operands should evaluate to " + expected;
		return dynamicTest(testName, () -> {
			var actual = arithmeticNode.evaluate();
			assertThat(actual).isEqualTo(expected);
		});
	}

	private static Stream<DynamicNode> generateTestsFor(
			Collection<ArithmeticNode> operands, ArithmeticTreeTestData treeTestData) {
		return operands.stream()
				.map(operand -> generateTestTreeFor(operand, treeTestData));
	}

}
