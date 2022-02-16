package vakcinac.io.core.utils;

public class StringUtils {
	public static boolean nullOrEmpty(String value) {
		return value == null || value.trim().equals("");
	}
}
