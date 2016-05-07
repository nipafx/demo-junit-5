package org.codefx.demo.junit5;

import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(OsCondition.class)
@Test
public @interface TestExceptOnOs {

	OS[] value() default {};

}
