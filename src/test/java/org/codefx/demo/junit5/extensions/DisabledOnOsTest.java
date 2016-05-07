package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.DisabledOnOs;
import org.codefx.demo.junit5.OS;
import org.codefx.demo.junit5.TestExceptOnOs;
import org.junit.gen5.api.Test;

import static org.junit.Assert.assertTrue;

class DisabledOnOsTest {

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
