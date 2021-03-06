package vakcinac.io.core.utils.parsers;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import vakcinac.io.core.Constants;
import vakcinac.io.core.utils.handlers.JaxBParsingHandler;
import vakcinac.io.core.utils.registries.NSRegistry;
import vakcinac.io.core.utils.registries.SchemeRegistry;

@SuppressWarnings("unchecked")
public class JaxBParser {
	private Class<?> genericClass;
	private Boolean indent;

	public JaxBParser(Class<?> genericClass) {
		this(genericClass, Boolean.TRUE);
	}
	
	public JaxBParser(Class<?> genericClass, Boolean indent) {
		this.genericClass = genericClass;
		this.indent = indent; 
	}

	public <T> T unmarshall(String text) {
		try {
			Unmarshaller unmarshaller = UnmarshallerFactory.newInstanceFor(genericClass);

			return (T) unmarshaller.unmarshal(new StringReader(text));
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to unmarshall %s with message: %s.\n", genericClass.getSimpleName(), e.getMessage());
		}

		return null;
	}

	public <T> T unmarshall(File file) {
		try {
			Unmarshaller unmarshaller = UnmarshallerFactory.newInstanceFor(genericClass);

			return (T) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to unmarshall %s with message: %s.\n", genericClass.getSimpleName(), e.getMessage());
		}

		return null;
	}

	private static class UnmarshallerFactory {
		public static Unmarshaller newInstanceFor(Class<?> forClass) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(forClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			try {
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);				
				Schema schema = schemaFactory.newSchema(new File(Constants.ROOT_RESOURCE + schemeRegistry.getPathFor(forClass)));
				unmarshaller.setSchema(schema);
				unmarshaller.setEventHandler(new JaxBParsingHandler());
			} catch (SAXException e) {

			}

			return unmarshaller;
		}
	}

	public <T> String marshall(T object) {
		try {
			Marshaller marshaller = MarshallerFactory.newInstanceFor(genericClass, this.indent);
			StringWriter writer = new StringWriter();

			marshaller.marshal(object, writer);

			return writer.toString();
		} catch (JAXBException e) {
			System.out.format("[ERROR]: An error has occured while trying to marshall %s with message: %s.\n", genericClass.getSimpleName(), e.getMessage());
		}

		return "";
	}

	private static class MarshallerFactory {

		public static Marshaller newInstanceFor(Class<?> forClass, Boolean indent) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(forClass);
			Marshaller marshaller = context.createMarshaller();

			if (!indent) {
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			}
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, indent);
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSRegistry());

			return marshaller;
		}
	}
}
