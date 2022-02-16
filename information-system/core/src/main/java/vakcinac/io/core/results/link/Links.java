package vakcinac.io.core.results.link;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "links")
@XmlAccessorType(XmlAccessType.FIELD)
public class Links {
	
    @XmlElement(name = "link")
    private List<String> link = new ArrayList<String>();

	public List<String> getLink() {
		return link;
	}

	public void setLink(List<String> link) {
		this.link = link;
	}

}