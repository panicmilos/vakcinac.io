package vakcinac.io.core.results.link;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document-links")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentLinksResult {
	
	@XmlElement(name = "referencing")
	private Links referencing;
	
	@XmlElement(name = "referenced-by")
	private Links referencedBy;

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
