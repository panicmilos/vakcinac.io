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

    protected abstract void initRegistry();
    protected abstract List<String> search(String graph, LogicalExpression expression);

    protected JenaRepository jenaRepository;

    public SearchService(JenaRepository jenaRepository) {
        this.jenaRepository = jenaRepository;
        initRegistry();
    }

    protected String formatQuery(String graph, LogicalExpression rootExpression) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIXES);
        stringBuilder.append(String.format(SELECT_TEMPLATE, graph));

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append("?s\n");

        for(String key : predicateUrlRegistry.keySet()) {
            filterBuilder.append(String.format("%s %s;\n", predicateUrlRegistry.get(key), key));
        }

        ArrayList<String> expressionStrings = new ArrayList<>();

        if (rootExpression.getOperators().size() != rootExpression.getExpressions().size() - 1 && !rootExpression.getOperators().contains("not")) {
            throw new BadLogicException("Neodgovarajuci broj logickih operatora");
        }

        String wrapper = "FILTER (%s) .\n";

        if (!rootExpression.getOperators().isEmpty() && rootExpression.getOperators().get(0).equals("not")) {
            popOperator(rootExpression.getOperators());
            wrapper = "FILTER (!(%s)) .\n";
        }

        for (LogicalExpression logicalExpression : rootExpression.getExpressions()) {
            if (!rootExpression.getOperators().isEmpty() && rootExpression.getOperators().get(0).equals("not")) {
                expressionStrings.add(popOperator(rootExpression.getOperators()));
            }
            String formattedExpression = buildExpression(logicalExpression);
            expressionStrings.add(formattedExpression);
            if (rootExpression.getOperators().isEmpty()) {
                continue;
            }
            expressionStrings.add(popOperator(rootExpression.getOperators()));
        }

        filterBuilder.append(String.format(wrapper, String.join(" ", expressionStrings)));

        stringBuilder.append(String.format(WHERE_TEMPLATE, filterBuilder));
        stringBuilder.append(GROUP_BY_SUBJECT);

        return stringBuilder.toString();
    }

    private String popOperator(List<String> operators) {
        String operator = operators.get(0);
        if (operator.equals("and")) {
            operator = "&&";
        } else if (operator.equals("or")) {
            operator = "||";
        } else if (operator.equals("not")) {
            operator = "!";
        }
        operators.remove(0);
        return operator;
    }

    private String buildExpression(LogicalExpression expression) {
        return getFilterForPredicates(expression.getPredicates(), expression.getOperators());
    }

    private String getFilterForPredicates(List<MetaPredicate> predicates, List<String> operators) {
        List<String> filters = new ArrayList<>();

        if (operators.size() != predicates.size() - 1 && !operators.contains("not")) {
            throw new BadLogicException("Neodgovarajuci broj logickih operatora");
        }

        String wrapper = "(%s)";

        if (!operators.isEmpty() && operators.get(0).equals("not")) {
            popOperator(operators);
            wrapper = "!(" + wrapper + ")";
        }

        for (MetaPredicate predicate : predicates) {
            if (!operators.isEmpty() && operators.get(0).equals("not")) {
                filters.add(popOperator(operators));
            }
            filters.add(getFilterForPredicate(predicate));
            if (operators.isEmpty()) {
                continue;
            }
            filters.add(popOperator(operators));
        }

        return String.format(wrapper, String.join(" ", filters));
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
