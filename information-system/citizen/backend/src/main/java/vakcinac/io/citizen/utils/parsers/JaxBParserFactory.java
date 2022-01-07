package vakcinac.io.citizen.utils.parsers;

public class JaxBParserFactory {

	public static JaxBParser newInstanceFor(Class<?> genericClass) {
		return new JaxBParser(genericClass);
	}
}
