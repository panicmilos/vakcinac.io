package vakcinac.io.core.utils;

import java.util.Random;

public class RandomUtils {
	public static String generateRandomNumericString(int length) {
        String SALTCHARS = "01234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
} 
}
