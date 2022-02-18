package vakcinac.io.core.results.link;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "links")
@XmlAccessorType(XmlAccessType.FIELD)
public class Links {
	
    @XmlElement(name = "link")
    private List<Link> link = new ArrayList<Link>();

	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "url",
        "id",
        "type",
        "createdAt"
    })
	public static class Link {
		
		@XmlElement(name = "url")
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

