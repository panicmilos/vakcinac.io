package vakcinac.io.core.requests;

import vakcinac.io.core.requests.helpers.LogicalExpression;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rdf-search-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaSearchRequest {

    @XmlElement
    private String graph;

    @XmlElement
    private LogicalExpression expression;

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public LogicalExpression getExpression() {
        return expression;
    }

    public void setExpression(LogicalExpression expression) {
        this.expression = expression;
    }
}
