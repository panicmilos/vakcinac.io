package vakcinac.io.core.results.doc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "citizen-documents-result")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitizenDocumentsResult {
	
	@XmlElement(name = "citizen-document")
	protected List<CitizenDocumentsResult.CitizenDocument> citizenDocument;

    public List<CitizenDocumentsResult.CitizenDocument> getCitizenDocument() {
        if (citizenDocument == null) {
        	citizenDocument = new ArrayList<CitizenDocumentsResult.CitizenDocument>();
        }
        return this.citizenDocument;
    }
    
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class CitizenDocument {

		
        @XmlValue
        protected String value;
        @XmlAttribute(name = "createdAt", required = true)
        protected String createdAt;
        
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
       
        public String getCreatedAt() {
            return createdAt;
        }
   
        public void setCreatedAt(String value) {
            this.createdAt = value;
        }
	}
}
