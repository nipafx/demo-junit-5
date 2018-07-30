package org.codefx.demo.junit5.integrations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;
import org.junitpioneer.jupiter.TempDirectory.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PioneerTest {

	@Test
	@ExtendWith(TempDirectory.class)
	void testTempDirInjection(@TempDir Path tempDir, TestReporter reporter) {
		assertNotNull(tempDir);
		reporter.publishEntry("Temporary directory", tempDir.toString());
	}

}
