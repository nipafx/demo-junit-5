package org.codefx.demo.junit5;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Character.toUpperCase;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class LambdaTest {

	private final List<DynamicTest> registeredTests = new ArrayList<>();

	protected void λ(String displayName, Executable test) {
		registeredTests.add(dynamicTest(displayName, test));
	}

	protected void λ(NamedTest test) {
		String displayName = test.prettyName();
		registeredTests.add(dynamicTest(displayName, () -> test.execute(displayName)));
	}

	@TestFactory
	List<DynamicTest> registeredTests() {
		return registeredTests;
	}

	@FunctionalInterface
	public interface NamedTest extends ParameterNameFinder {

		void execute(String name);

	}

	public interface ParameterNameFinder extends MethodFinder {

		default String name() {
			return parameterName(0);
		}

		default String prettyName() {
			return stream(name().split("_"))
					.map(word -> toUpperCase(word.charAt(0)) + word.substring(1))
					.collect(joining(" "));
		}

	}

	public interface MethodFinder extends Serializable {

		// as seen on http://benjiweber.co.uk/blog/2015/08/17/lambda-parameter-names-with-reflection/

		default SerializedLambda serialized() {
			try {
				Method replaceMethod = getClass().getDeclaredMethod("writeReplace");
				replaceMethod.setAccessible(true);
				return (SerializedLambda) replaceMethod.invoke(this);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		default Class<?> getContainingClass() {
			try {
				String className = serialized().getImplClass().replaceAll("/", ".");
				return Class.forName(className);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		default Method method() {
			SerializedLambda lambda = serialized();
			Class<?> containingClass = getContainingClass();
			return stream(containingClass.getDeclaredMethods())
					.filter(method -> Objects.equals(method.getName(), lambda.getImplMethodName()))
					.findFirst()
					.orElseThrow(UnableToGuessMethodException::new);
		}

		default Parameter parameter(int n) {
			return method().getParameters()[n];
		}

		default String parameterName(int n) {
			return checkParameterNamesEnabled(parameter(n).getName());
		}

		default String checkParameterNamesEnabled(String name) {
			if ("arg0".equals(name)) {
				throw new IllegalStateException(
						"You need to compile with javac (at least 8u60 but before 9)"
								+ " with the option -parameters for parameter reflection to work.");
			}
			return name;
		}

		class UnableToGuessMethodException extends RuntimeException {

		}
	}

}
