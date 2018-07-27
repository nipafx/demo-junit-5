package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CollectExceptionExtension.class)
@ExtendWith(DisabledIfTestFailedWithCondition.class)
public @interface DisabledIfTestFailedWith {

	Class<? extends Exception>[] value();

}
