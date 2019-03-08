package org.codefx.demo.junit5.scenario;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class StepMethodOrderer implements MethodOrderer, TestExecutionExceptionHandler, ExecutionCondition {

	// TODO: use extension context to save state
	private boolean somethingFailed = false;

	@Override
	public void orderMethods(MethodOrdererContext context) {
		Collection<DirectedNode> tests = directedTests(context);
		List<MethodDescriptor> orderedTests = topologicalSort(tests).stream()
				.map(DirectedNode::testMethod)
				.collect(toList());
		context.getMethodDescriptors().sort(Comparator.comparing(orderedTests::indexOf));
	}

	// TODO: consider parallel execution with `getDefaultExecutionMode`

	// TODO: find a better name for `DirectedNode` and this method
	private Collection<DirectedNode> directedTests(MethodOrdererContext context) {
		Map<String, DirectedNode> tests = new HashMap<>();
		Map<String, Set<String>> nextEdges = new HashMap<>();

		context
				.getMethodDescriptors()
				.forEach(testMethod -> {
					String testName = testMethod.getMethod().getName();
					tests.put(testName, new DirectedNode(testMethod));
					// TODO handle absence of `@Step`
					testMethod
							.findAnnotation(Step.class).stream()
							.flatMap(step -> Stream.of(step.next()))
							// TODO: filter seems unnecessary because by default `next` is empty
							.filter(not(String::isEmpty))
							.forEach(nextTestName -> nextEdges
									.computeIfAbsent(testName, __ -> new HashSet<>())
									.add(nextTestName));
					testMethod
							.findAnnotation(Step.class).stream()
							.flatMap(step -> Stream.of(step.after()))
							// TODO: filter seems unnecessary because by default `after` is empty
							.filter(not(String::isEmpty))
							.forEach(beforeTestName -> nextEdges
									.computeIfAbsent(beforeTestName, __ -> new HashSet<>())
									.add(testName));
				});
		nextEdges.forEach((testMethodName, nextTestNames) -> {
			nextTestNames.stream()
					// TODO: handle missing nodes, which come from wrong "next" attribute values
					.map(nextTestName -> {
						DirectedNode nextTestNode = tests.get(nextTestName);
						if (nextTestNode == null)
							throw new IllegalArgumentException("There is no test with name '" + nextTestName + "'.");
						else
							return nextTestNode;
					})
					.forEach(nextTestNode -> tests.get(testMethodName).children().add(nextTestNode));
		});

		return tests.values();
	}

	// TODO: check details
	// source: https://gist.github.com/wadejason/36bfe5fb0f119de409492dd7b14d6120
	public List<DirectedNode> topologicalSort(Collection<DirectedNode> nodes) {
		// write your code here
		ArrayList<DirectedNode> order = new ArrayList<>();

		if (nodes == null) {
			return order;
		}

		// 1. collect indegree
		// Map<DirectedNode, Integer> indegree = new HashMap<>();
		// for (DirectedNode node : nodes) {
		//     indegree.put(node, 0);
		// }
		// for (DirectedNode node : nodes) {
		//     for (DirectedNode neighbor : node.nieghbors) {
		//         indegree.put(neighbor, map.get(neighbor) + 1);
		//     }
		// }
		Map<DirectedNode, Integer> indegree = getIndegree(nodes);

		// 2. put all nodes that indegree = 0 into queue;
		Queue<DirectedNode> queue = new LinkedList<>();

		for (DirectedNode node : nodes) {
			if (indegree.get(node) == 0) {
				queue.offer(node);
				order.add(node);
			}
		}

		// 3. Topological Sorting - BFS
		while (!queue.isEmpty()) {
			DirectedNode node = queue.poll();
			for (DirectedNode neighbor : node.children()) {
				// everytime we need to indegree - 1
				indegree.put(neighbor, indegree.get(neighbor) - 1);
				if (indegree.get(neighbor) == 0) {
					queue.offer(neighbor);
					order.add(neighbor);
				}
			}
		}
		//  nodes is cyclic or not
		if (order.size() == nodes.size()) {
			return order;
		}
		return null;
	}

	private Map<DirectedNode, Integer> getIndegree(Collection<DirectedNode> graph) {
		Map<DirectedNode, Integer> indegree = new HashMap<>();
		for (DirectedNode node : graph) {
			indegree.put(node, 0);
		}

		for (DirectedNode node : graph) {
			for (DirectedNode neighbor : node.children()) {
				indegree.put(neighbor, indegree.get(neighbor) + 1);
			}
		}
		return indegree;
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		somethingFailed = true;
		throw throwable;
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(
			ExtensionContext context) {
		// TODO only deactivate tests that are in the subtree below the failed test
		return somethingFailed
				? ConditionEvaluationResult.disabled("An earlier scenario test failed")
				: ConditionEvaluationResult.enabled("");
	}

	private static class DirectedNode {

		private final Set<DirectedNode> children;
		private final MethodDescriptor testMethod;

		private DirectedNode(MethodDescriptor testMethod) {
			this.testMethod = requireNonNull(testMethod);
			this.children = new HashSet<>();
		}

		public Set<DirectedNode> children() {
			return children;
		}

		public MethodDescriptor testMethod() {
			return testMethod;
		}

	}

}
