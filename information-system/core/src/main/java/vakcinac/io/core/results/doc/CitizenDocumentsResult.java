package vakcinac.io.core.results.doc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
        "link",
        "id",
        "type",
        "createdAt"
    })
    public static class CitizenDocument {

		
        @XmlElement(name = "link")
        protected String link;
        @XmlElement(name = "id")
        protected String id;
        @XmlElement(name = "type")
        protected String type;
        @XmlElement(name = "created-at", required = true)
        protected String createdAt;
        
        public String getLink() {
            return link;
        }
        
        public void setLink(String value) {
        	this.link = value;
        }
        
        public String getId() {
            return id;
        }
        
        public void setId(String value) {
        	this.id = value;
        }
        
        public String getType() {
        	return type;
        }
        
        public void setType(String value) {
        	this.type = value;
        }
       
        public String getCreatedAt() {
            return createdAt;
        }
   
        public void setCreatedAt(String value) {
            this.createdAt = value;
        }
	}
}
