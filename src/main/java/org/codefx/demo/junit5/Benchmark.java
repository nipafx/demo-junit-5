package org.codefx.demo.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Benchmarks tests and test classes by printing the run time to the console.
 * <p>
 * If applied to a test, it will report the run time of the individual test without before and after behavior.
 * If applied to a class, it will report the cumulated run time of all tests therein.
 */
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(BenchmarkExtension.class)
public @interface Benchmark { }
