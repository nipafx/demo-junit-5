package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ExpectedExceptionExtension.class)
@org.junit.jupiter.api.Test
public @interface Test {

	class None extends Throwable {

		private static final long serialVersionUID = 1L;

		private None() {
		}
	}

	/**
	 * Optionally specify <code>expected</code>, a Throwable, to cause a test method to succeed if
	 * and only if an exception of the specified class is thrown by the method.
	 */
	Class<? extends Throwable> expected() default None.class;

}
