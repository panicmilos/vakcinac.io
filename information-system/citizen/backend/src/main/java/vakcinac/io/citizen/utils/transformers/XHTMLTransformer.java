package vakcinac.io.citizen.utils.transformers;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.utils.parsers.DocumentParser;
import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;
import vakcinac.io.citizen.utils.registries.XsltRegistry;

public class XHTMLTransformer {

	private TransformerFactory transformerFactory;
	private DocumentParser documentParser;
	private XsltRegistry xsltRegistry;

	public XHTMLTransformer() {
		transformerFactory = TransformerFactory.newInstance();
		documentParser = new DocumentParser();
		xsltRegistry = new XsltRegistry();
	}

	public byte[] generate(Object obj) {

		try {
			String serializedObj = serializeSource(obj);

			StreamSource transformSource = new StreamSource(new File(findXslPathFor(obj)));
			Transformer transformer = createTransformerFor(transformSource);
			
			Document document = documentParser.parse(serializedObj.getBytes());
			DOMSource source = new DOMSource(document);

			ByteArrayOutputStream xhtmlResult = new ByteArrayOutputStream();
			transformer.transform(source, new StreamResult(xhtmlResult));

			return xhtmlResult.toByteArray();

		} catch (TransformerException te) {
			te.printStackTrace();
		}

		return null;

	}
	
	private String serializeSource(Object obj) {
		JaxBParser parser = JaxBParserFactory.newInstanceFor(obj.getClass());
		
		return parser.marshall(obj);
	}

	private String findXslPathFor(Object obj) {
		Class<?> objClass = obj.getClass();
		
		return Constants.ROOT_RESOURCE + xsltRegistry.getPathFor(objClass);
	}
	
	private Transformer createTransformerFor(StreamSource transformSource) throws TransformerConfigurationException {
		Transformer transformer = transformerFactory.newTransformer(transformSource);
		
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

		return transformer;

	}
}
