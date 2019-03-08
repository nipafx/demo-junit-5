package org.codefx.demo.junit5.scenario;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Test
public @interface Step {

	/*
	 * TODO: also define "after"
	 *
	 *   @Step(after = "foo")
	 *   void bar() {
	 *       // ... test
	 *   }
	 */

	String[] next() default { "" };

}
