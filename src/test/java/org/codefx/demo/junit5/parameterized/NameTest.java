package org.codefx.demo.junit5.parameterized;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NameTest {

	@ParameterizedTest
	@CsvSource({ "One, 1", "Two, 2" })
	void testDefault(String word, int number) { }

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@CsvSource({ "One, 1", "Two, 2" })
	void testVerbose(String word, int number) { }

	@ParameterizedTest(name = "{index}")
	@CsvSource({ "One, 1", "Two, 2" })
	void testIndex(String word, int number) { }

	@ParameterizedTest(name = "{0}: {1}")
	@CsvSource({ "One, 1", "Two, 2" })
	void testSpecificArguments(String word, int number) { }

	@ParameterizedTest(name = "{arguments}")
	@CsvSource({ "One, 1", "Two, 2" })
	void testAllArguments(String word, int number) { }

	@DisplayName("Roman numeral")
	@ParameterizedTest(name = "\"{0}\" should be {1}")
	@CsvSource({ "I, 1", "II, 2", "V, 5" })
	void testWithDisplayName(String roman, int arabic) { }

}
