package vakcinac.io.core.requests;

import vakcinac.io.core.requests.helpers.RdfPredicate;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "rdf-search-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RdfSearchRequest {

    @XmlElement
    private String graph;

    @XmlElementWrapper(name = "predicates")
    @XmlElement(name = "rdf-predicate")
    private List<RdfPredicate> predicates;

    public RdfSearchRequest() {
        predicates = new ArrayList<RdfPredicate>();
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public List<RdfPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<RdfPredicate> predicates) {
        this.predicates = predicates;
    }
}
