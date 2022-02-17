package vakcinac.io.core.factories;

import vakcinac.io.core.results.doc.PreviewDocumentResult;
import vakcinac.io.core.results.link.Links;
import vakcinac.io.core.utils.transformers.PDFTransformer;
import vakcinac.io.core.utils.transformers.XHTMLTransformer;

public class PreviewDocumentResultFactory {
	
	public static PreviewDocumentResult create(Object obj, Links referencing, Links referencedBy, String type) {
		PDFTransformer pdfTransformer = new PDFTransformer();
		XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
		
		PreviewDocumentResult result = new PreviewDocumentResult();
		
		if (type.equals("pdf")) {
			byte[] pdf = pdfTransformer.generate(obj);
			result.setContent(pdf);
		} else {
			byte[] xhtml = xhtmlTransformer.generate(obj);
			result.setContent(xhtml);
		}
		
		result.setReferencedBy(referencedBy);
		result.setReferencing(referencing);
		
		return result;
	}

}
