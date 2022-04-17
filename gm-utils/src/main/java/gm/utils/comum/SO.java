package gm.utils.comum;

import gm.utils.string.UString;

public class SO {
	private static String get(){
		return System.getProperty("os.name");
	}
	public static boolean linux(){
		return UString.containsIgnoreCase(get(), "linux");
	}
	public static boolean windows(){
		return UString.containsIgnoreCase(get(), "windows");
	}
	public static String barra() {
		return windows() ? "\\" : "/";
	}
}
