package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.DisabledOnOs;
import org.codefx.demo.junit5.OS;
import org.codefx.demo.junit5.TestExceptOnOs;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit5.class)
public class DisabledOnOsTest {

	@Test
	void runsOnAllOS() {
		assertTrue(true);
	}

	@Test
	@DisabledOnOs(OS.NIX)
	void doesNotRunOnLinux() {
		assertTrue(false);
	}

	@Test
	@DisabledOnOs(OS.WINDOWS)
	void doesNotRunOnWindows() {
		assertTrue(false);
	}

	@TestExceptOnOs(OS.NIX)
	void doesNotRunOnLinuxEither() {
		assertTrue(false);
	}

	@TestExceptOnOs(OS.WINDOWS)
	void doesNotRunOnWindowsEither() {
		assertTrue(false);
	}

}
