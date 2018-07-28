package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.DisabledByFormula;
import org.codefx.demo.junit5.OS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.codefx.demo.junit5.DisabledByFormula.disabledWhen;
import static org.junit.jupiter.api.Assertions.fail;

class DisabledByFormulaTest {

	private static final LocalDateTime MAYAN_B_AK_TUN_13 = LocalDateTime.of(2012, 12, 21, 0, 0);

	@RegisterExtension
	static final DisabledByFormula FORMULA = disabledWhen(
			"After Mayan b'ak'tun 13 and on Linux",
			now().isAfter(MAYAN_B_AK_TUN_13) && OS.determine() == OS.NIX);

	@Test
	void doom_failsOnNonUnix() {
		fail("DOOM! 😱");
	}

}
