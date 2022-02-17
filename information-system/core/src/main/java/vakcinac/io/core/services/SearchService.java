package vakcinac.io.core.services;

import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.requests.helpers.LogicalExpression;
import vakcinac.io.core.requests.helpers.MetaPredicate;

import java.util.*;

public abstract class SearchService {

    protected HashMap<String, String> predicateTypeRegistry = new HashMap<>();
    protected HashMap<String, String> predicateUrlRegistry = new HashMap<>();

    protected static final String PREFIXES = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
    protected static final String SELECT_TEMPLATE = "SELECT DISTINCT ?s FROM <%s>\n";
    protected static final String WHERE_TEMPLATE = "WHERE { %s }\n";
    protected static final String GROUP_BY_SUBJECT = "GROUP BY ?s";

    // types
    protected static final String XSD_DATE = "xsd:date";
    protected static final String XSD_INTEGER = "xsd:integer";
    protected static final String XSD_STRING = "xsd:string";
    protected static final String LINK = "link";

    protected static final String DATE_COMPARISON_TEMPLATE = "(%s %s '%s'^^%s)";
    protected static final String INTEGER_COMPARISON_TEMPLATE = "(%s %s %s)";

    protected abstract void initRegistry();
    protected abstract List<String> search(String graph, List<LogicalExpression> expressions);

    protected JenaRepository jenaRepository;

    public SearchService(JenaRepository jenaRepository) {
        this.jenaRepository = jenaRepository;
        initRegistry();
    }

    protected String formatQuery(String graph, List<LogicalExpression> expressions) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIXES);
        stringBuilder.append(String.format(SELECT_TEMPLATE, graph));

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append("?s\n");

        for(String key : predicateUrlRegistry.keySet()) {
            filterBuilder.append(String.format("%s %s;\n", predicateUrlRegistry.get(key), key));
        }

        for (LogicalExpression logicalExpression : expressions) {
            String formattedExpression = buildExpression(logicalExpression);
            filterBuilder.append(formattedExpression);
        }

        stringBuilder.append(String.format(WHERE_TEMPLATE, filterBuilder));
        stringBuilder.append(GROUP_BY_SUBJECT);

        return stringBuilder.toString();
    }

    private String buildExpression(LogicalExpression expression) {
        if (expression.getType().equals("and")) {
            return getFilterForAndPredicates(expression.getPredicates());
        } else {
            return getFilterForOrPredicates(expression.getPredicates());
        }
    }

    private String getFilterForAndPredicates(List<MetaPredicate> predicates) {

        if (predicates.size() < 2) {
            throw new BadLogicException("Premalo predikata za logicko i.");
        }

        List<String> filters = new ArrayList<>();
        for (MetaPredicate predicate : predicates) {
            filters.add(getFilterForPredicate(predicate));
        }

        return String.format("(%s)", String.join(" && ", filters));
    }

    private String getFilterForOrPredicates(List<MetaPredicate> predicates) {

        if (predicates.size() < 2) {
            throw new BadLogicException("Premalo predikata za logicko ili.");
        }

        List<String> filters = new ArrayList<>();
        for (MetaPredicate predicate : predicates) {
            filters.add(getFilterForPredicate(predicate));
        }

        return String.format("(%s)", String.join(" || ", filters));
    }

    private String getFilterForPredicate(MetaPredicate predicate) {

        if (!predicateTypeRegistry.containsKey(predicate.getVariable())) {
            throw new BadLogicException(String.format("Nije podrzana pretraga po varijabli %s", predicate.getVariable()));
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_STRING) || predicateTypeRegistry.get(predicate.getVariable()).equals(LINK)) {
            return String.format("%s(lcase(str(%s)), lcase(str('%s')))", predicate.getOperator(), predicate.getVariable(), predicate.getValue());
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_INTEGER)) {
            return String.format("(%s %s %s)", predicate.getVariable(), predicate.getOperator(), predicate.getValue());
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_DATE)) {
            return String.format("(%s %s '%s'^^%s)", predicate.getVariable(), predicate.getOperator(), predicate.getValue(), XSD_DATE);
        }

        return "";
    }

}
