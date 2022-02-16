package vakcinac.io.core.mail;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MailAttachment {
    
    @XmlElement(name = "filename")
    private String filename;
    @XmlElement(name = "path")
    private String path;

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
