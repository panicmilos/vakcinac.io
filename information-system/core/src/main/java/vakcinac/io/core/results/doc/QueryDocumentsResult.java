package vakcinac.io.core.results.doc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "documents")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryDocumentsResult {
	@XmlElement(name = "document")
	protected List<QueryDocumentsResult.Document> document;

    public List<QueryDocumentsResult.Document> getDocument() {
        if (document == null) {
        	document = new ArrayList<QueryDocumentsResult.Document>();
        }
        return this.document;
    }
    
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "url",
        "id",
        "type",
        "createdAt"
    })
    public static class Document {

		
		@XmlElement
        protected String url;
		@XmlElement(name = "id")
        protected String id;
        @XmlElement(name = "type")
        protected String type;
        @XmlElement(name = "created-at", required = true)
        protected String createdAt;
        
        public String getUrl() {
            return url;
        }

        public void setUrl(String value) {
            this.url = value;
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
