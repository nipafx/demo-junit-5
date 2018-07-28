package org.codefx.demo.junit5.basics;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DisableTest {

	@Test
	@Disabled("Y U No Pass?!")
	void failingTest() {
		assertTrue(false);
	}

	@Test
	@EnabledOnOs(OS.LINUX)
	@DisabledOnJre(JRE.JAVA_10)
	void conflictingConditions_executed() {
		assertTrue(true);
	}

}
