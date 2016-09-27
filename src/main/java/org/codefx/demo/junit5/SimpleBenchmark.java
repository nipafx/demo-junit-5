package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Benchmarks individual tests by printing the run time to the console.
 * <p>
 * If applied to a container, all tests therein will be benchmarked individually.
 */
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SimpleBenchmarkExtension.class)
public @interface SimpleBenchmark { }
