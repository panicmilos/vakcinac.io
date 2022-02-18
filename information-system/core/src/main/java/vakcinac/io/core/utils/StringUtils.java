package vakcinac.io.core.utils;

public class StringUtils {
	public static boolean nullOrEmpty(String value) {
		return value == null || value.trim().equals("");
	}
	
	public static boolean notNullOrEmpty(String value) {
		System.out.println(value + " " + !nullOrEmpty(value));
		return !nullOrEmpty(value);
	}
}
