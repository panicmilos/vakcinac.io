package vakcinac.io.core.results.doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import vakcinac.io.core.results.link.Links;

@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
public class PreviewDocumentResult {
	
	@XmlElement(name = "content")
	private byte[] content;
	
	@XmlElement(name = "referencing")
	private Links referencing;
	
	@XmlElement(name = "referenced-by")
	private Links referencedBy;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Links getReferencing() {
		return referencing;
	}

	public void setReferencing(Links referencing) {
		this.referencing = referencing;
	}

	public Links getReferencedBy() {
		return referencedBy;
	}

	public void setReferencedBy(Links referencedBy) {
		this.referencedBy = referencedBy;
	}

}
