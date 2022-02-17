package vakcinac.io.core.factories;

import vakcinac.io.core.results.doc.CitizenDocumentsResult.CitizenDocument;

public class CitizenDocumentFactory {

	public static CitizenDocument create(String link, String createdAt) {
		CitizenDocument document = new CitizenDocument();
		
		document.setValue(link);
		document.setCreatedAt(createdAt);
		
		return document;
	}
}
