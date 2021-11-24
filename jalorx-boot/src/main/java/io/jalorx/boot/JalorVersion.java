package io.jalorx.boot;

/**
 * 
 * @author chenb
 *
 */
public final class JalorVersion {

	public static final String TAG     = "5.0.0-BUILD-SNAPSHOT";
	public static final String VERSION = "v3";
	public static final String LOGO    = " ::JalorX Boot::";

	//
	private static final String[] BANNER = {
		"    ___       _          __   __",
		"   |_  |     | |         \\ \\ / /",
		"     | | __ _| | ___  _ __\\ V / ",
		"     | |/ _` | |/ _ \\| '__/   \\ ",
		" /\\__/ / (_| | | (_) | | / /^\\ \\",
		" \\____/ \\__,_|_|\\___/|_| \\/   \\/",
		};

	private JalorVersion() {}

	static void printBanner() {
		for (String line : BANNER) {
			System.out.println(line);
		}
		int           length  = BANNER[BANNER.length - 1].length() - JalorVersion.getTag()
				.length() - LOGO.length();
		StringBuilder builder = new StringBuilder(30);
		builder.append(LOGO);
		for (int i = 0; i < length - 2; i++) {
			builder.append(" ");
		}
		builder.append("(")
				.append(JalorVersion.getTag())
				.append(")\n");
		System.out.println(builder);
	}

	public static String getVersion() {
		return VERSION;
	}

	public static String getTag() {
		return TAG;
	}

	public static void main(String[] args) {
		printBanner();
	}

}
