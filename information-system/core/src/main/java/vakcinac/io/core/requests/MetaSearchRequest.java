package vakcinac.io.core.requests;

import vakcinac.io.core.requests.helpers.LogicalExpression;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "rdf-search-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaSearchRequest {

    @XmlElement
    private String graph;

    @XmlElementWrapper(name = "expressions")
    @XmlElement(name = "expression")
    private List<LogicalExpression> expressions;

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public List<LogicalExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<LogicalExpression> expressions) {
        this.expressions = expressions;
    }
}
