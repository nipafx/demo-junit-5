package org.codefx.demo.junit5.scenario;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioTest
class ScenarioExtensionTests {

	private boolean loggedIn;
	private List<String> actions = new ArrayList<>();

	@Step
	void loginTest() {
		loggedIn = true;
	}

	@Step(next = { "action_B", "action_C" })
	void action_A() {
		assertThat(loggedIn).isTrue();
		actions.add("a");
	}

	@Step(next = "logoutTest")
	void action_B() {
		assertThat(loggedIn).isTrue();
		assertThat(actions.size()).isGreaterThan(0);
		actions.add("b");
		throw new RuntimeException();
	}

	@Step(next = "logoutTest")
	void action_C() {
		assertThat(loggedIn).isTrue();
		assertThat(actions.size()).isGreaterThan(0);
		actions.add("c");
	}

	@Step(next = "verify")
	void logoutTest() {
		assertThat(loggedIn).isTrue();
		loggedIn = false;
	}

	@Step
	void verify() {
		assertThat(loggedIn).isFalse();
		assertThat(actions).hasSize(3);
	}

}
