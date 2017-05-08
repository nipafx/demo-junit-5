package org.codefx.demo.junit5.extensions;

import org.codefx.demo.junit5.IntegrationTest;

class Integration {

	@IntegrationTest
	void sleep() throws InterruptedException {
		System.out.println("You should see a report entry informing you of the test's run time.");
	}

}
