package org.codefx.demo.junit4;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.ExpectedException;

import java.util.List;

@EnableRuleMigrationSupport
class JUnit4RuleInJupiter {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	void useExpectedExceptionRule() {
		List<Object> list = List.of();

		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Index 0 out of bounds for length 0");
		// this call fails
		list.get(0);
	}

}
