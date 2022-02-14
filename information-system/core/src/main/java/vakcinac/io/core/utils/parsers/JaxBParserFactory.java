package vakcinac.io.core.utils.parsers;

public class JaxBParserFactory {

	public static JaxBParser newInstanceFor(Class<?> genericClass) {
		return new JaxBParser(genericClass);
	}
	
	public static JaxBParser newInstanceFor(Class<?> genericClass, Boolean indent) {
		return new JaxBParser(genericClass, indent);
	}
}
