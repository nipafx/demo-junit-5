package org.codefx.demo.junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LegacyTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testWithJUnit4_failsIfNotRunWithJUnit5Platform() {
		Optional<String> vintageFrame = StackWalker
				.getInstance()
				.walk(frames -> frames
						.peek(System.out::println)
						.map(StackFrame::getClassName)
						.filter(method -> method.contains("vintage"))
						.findAny());
		assertThat(vintageFrame).isNotEmpty();
	}

	@Test
	public void useExpectedExceptionRule() {
		List<Object> list = List.of();

		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Index 0 out-of-bounds for length 0");
		// this call fails
		list.get(0);
	}

}
