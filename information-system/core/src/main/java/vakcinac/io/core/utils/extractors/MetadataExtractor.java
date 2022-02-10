package vakcinac.io.core.utils.extractors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;
import vakcinac.io.core.Constants;

public class MetadataExtractor {
	private TransformerFactory transformerFactory;

	private static final String XSLT_FILE = Constants.ROOT_RESOURCE + "/grddl.xsl";

	public MetadataExtractor() {
		transformerFactory = new TransformerFactoryImpl();
	}

	public byte[] extract(String input) {
		try {
			StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
			Transformer transformer = createTransformerFor(transformSource);

			StreamSource source = new StreamSource(new ByteArrayInputStream(input.getBytes()));

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			transformer.transform(source, new StreamResult(result));

			return result.toByteArray();
		} catch (TransformerException te) {
			te.printStackTrace();
		}

		return null;
	}

	private Transformer createTransformerFor(StreamSource transformSource) throws TransformerConfigurationException {
		Transformer transformer = transformerFactory.newTransformer(transformSource);

		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		return transformer;
	}
}
