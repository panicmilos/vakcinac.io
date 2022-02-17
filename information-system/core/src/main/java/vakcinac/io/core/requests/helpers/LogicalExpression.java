package vakcinac.io.core.requests.helpers;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class LogicalExpression {

    @XmlElementWrapper(name = "operators")
    @XmlElement(name = "operator")
    private List<String> operators;

    @XmlElement(name = "is-root")
    private Boolean isRoot;

    @XmlElementWrapper(name = "expressions")
    @XmlElement(name = "expression")
    List<LogicalExpression> expressions;

    @XmlElementWrapper(name = "predicates")
    @XmlElement(name = "predicate")
    List<MetaPredicate> predicates;

    public LogicalExpression() {
        operators = new ArrayList<>();
        predicates = new ArrayList<>();
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }

    public List<MetaPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<MetaPredicate> predicates) {
        this.predicates = predicates;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public List<LogicalExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<LogicalExpression> expressions) {
        this.expressions = expressions;
    }
}
