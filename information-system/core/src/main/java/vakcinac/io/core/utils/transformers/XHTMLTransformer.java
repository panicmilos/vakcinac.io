package vakcinac.io.core.utils.transformers;

import org.w3c.dom.Document;
import vakcinac.io.core.Constants;
import vakcinac.io.core.models.trans.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.core.utils.parsers.DocumentParser;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;
import vakcinac.io.core.utils.registries.XsltRegistry;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
			StreamSource transformSource;

			if (obj.getClass().getSimpleName().equals("ZahtevZaIzdavanjeZelenogSertifikata")) {
				String fileLines = String.join("\n", Files.readAllLines(Paths.get(findXslPathFor(obj))));

				JaxBParser parser = JaxBParserFactory.newInstanceFor(ZahtevZaIzdavanjeZelenogSertifikata.class);
				ZahtevZaIzdavanjeZelenogSertifikata zahtev = parser.unmarshall(serializedObj);

				String[] parts = fileLines.split("xml-je-odrvatno-los");
				String serializedZahtev = parts[0] + zahtev.getRazlog() + parts[1];

				ByteArrayInputStream byteReader = new ByteArrayInputStream(serializedZahtev.getBytes());
				transformSource = new StreamSource(byteReader);
			} else {
				transformSource = new StreamSource(new File(findXslPathFor(obj)));
			}
			
			Transformer transformer = createTransformerFor(transformSource);
			
			Document document = documentParser.parse(serializedObj.getBytes());
			DOMSource source = new DOMSource(document);

			ByteArrayOutputStream xhtmlResult = new ByteArrayOutputStream();
			transformer.transform(source, new StreamResult(xhtmlResult));

			return xhtmlResult.toByteArray();

		} catch (TransformerException te) {
			te.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
