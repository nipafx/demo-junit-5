package org.codefx.demo.junit5;

public enum OS {

	/*
	 * This class was written for demonstration purposes.
	 * It is not at all fit for production!
	 */

	NIX,
	MAC,
	WINDOWS;

	public static OS determine() {
		String os = System.getProperty("os.name").toLowerCase();

		if (isWindows(os)) {
			return WINDOWS;
		} else if (isMac(os)) {
			return MAC;
		} else if (isUnix(os)) {
			return NIX;
		} else {
			throw new IllegalArgumentException("Unknown OS \"" + os + "\".");
		}
	}

	private static boolean isWindows(String os) {
		return os.contains("win");
	}

	private static boolean isMac(String os) {
		return os.contains("mac");
	}

	private static boolean isUnix(String os) {
		return os.contains("nix") || os.contains("nux");
	}

}
