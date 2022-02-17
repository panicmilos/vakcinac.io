package vakcinac.io.core.requests.helpers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class LogicalExpression {

    @XmlElement
    private String type;

    @XmlElementWrapper(name = "predicates")
    @XmlElement(name = "predicate")
    List<MetaPredicate> predicates;

    public LogicalExpression() {
        predicates = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MetaPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<MetaPredicate> predicates) {
        this.predicates = predicates;
    }
}
