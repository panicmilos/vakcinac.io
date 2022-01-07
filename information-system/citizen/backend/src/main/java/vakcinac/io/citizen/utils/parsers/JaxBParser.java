package vakcinac.io.citizen.utils.parsers;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


@SuppressWarnings("unchecked")
public class JaxBParser {
	private Class<?> genericClass;
	
	public JaxBParser(Class<?> genericClass) {
		this.genericClass = genericClass;
	}
	
	public <T> T unmarshall(String text) {
		try {
			var unmarshaller = UnmarshallerFactory.newInstanceFor(genericClass);

			return (T) unmarshaller.unmarshal(new StringReader(text));
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to unmarshall %s with message: %s.", genericClass.getSimpleName(), e.getMessage());
		}
		
		return null;
	}
	
	public <T> T unmarshall(File file) {
		try {
			var unmarshaller = UnmarshallerFactory.newInstanceFor(genericClass);

			return (T) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to unmarshall %s with message: %s.", genericClass.getSimpleName(), e.getMessage());
		}
		
		return null;
	}
	
	private static class UnmarshallerFactory {
		public static Unmarshaller newInstanceFor(Class<?> forClass) throws JAXBException {
			var context = JAXBContext.newInstance(forClass);
			return context.createUnmarshaller();
		}
	}
	
	
	public <T> String marshall(T object) {
		try {
			var marshaller = MarshallerFactory.newInstanceFor(genericClass);
			var writer = new StringWriter();
			
			marshaller.marshal(object, writer);
			
			return writer.toString();
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to marshall %s with message: %s.", genericClass.getSimpleName(), e.getMessage());
		}
		
		return "";
	}
	
	private static class MarshallerFactory {
		public static Marshaller newInstanceFor(Class<?> forClass) throws JAXBException {
			var context = JAXBContext.newInstance(forClass);
			var marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			return marshaller;
		}
	}
}
