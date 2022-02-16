package vakcinac.io.core.mail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "mail")
@XmlAccessorType(XmlAccessType.FIELD)
public class MailContent {
    @XmlElement(name = "to")
    private String to;
    @XmlElement(name = "subject")
    private String subject;
    @XmlElement(name = "text")
    private String text;
    @XmlElement(name = "attachments")
    private List<MailAttachment> attachments;

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public List<MailAttachment> getAttachments() {
        return attachments;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttachments(List<MailAttachment> attachments) {
        this.attachments = attachments;
    }

}
