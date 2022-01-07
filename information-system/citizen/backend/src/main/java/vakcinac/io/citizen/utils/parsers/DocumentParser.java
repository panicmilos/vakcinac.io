package vakcinac.io.citizen.utils.parsers;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DocumentParser {
	private DocumentBuilderFactory documentFactory;
	
	public DocumentParser() {
		documentFactory = DocumentBuilderFactory.newInstance();
		
		documentFactory.setNamespaceAware(true);
		documentFactory.setIgnoringComments(true);
		documentFactory.setIgnoringElementContentWhitespace(true);
	}
	
	public Document parse(byte[] byteArray) {
		try {

			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(byteArray));

			if (document != null) {
				System.out.println("[INFO] File parsed with no errors.");
				return document;
			}
			
			System.out.println("[WARN] Document is null.");
		} catch (Exception e) {
		}

		return null;
	}

}
