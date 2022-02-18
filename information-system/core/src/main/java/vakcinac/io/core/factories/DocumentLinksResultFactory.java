package vakcinac.io.core.factories;

import vakcinac.io.core.results.link.DocumentLinksResult;
import vakcinac.io.core.results.link.Links;

public class DocumentLinksResultFactory {
	
	public static DocumentLinksResult create(Links referencing, Links referencedBy) {
		DocumentLinksResult result = new DocumentLinksResult();
		
		result.setReferencedBy(referencedBy);
		result.setReferencing(referencing);
		
		return result;
	}

}
