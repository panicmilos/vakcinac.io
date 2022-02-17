package vakcinac.io.core.services;

import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.requests.helpers.RdfPredicate;

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
    protected abstract List<String> search(String graph, List<RdfPredicate> predicates);

    protected JenaRepository jenaRepository;

    public SearchService(JenaRepository jenaRepository) {
        this.jenaRepository = jenaRepository;
        initRegistry();
    }

    protected String formatQuery(String graph, List<RdfPredicate> predicates) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIXES);
        stringBuilder.append(String.format(SELECT_TEMPLATE, graph));

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append("?s\n");

        for(String key : predicateUrlRegistry.keySet()) {
            filterBuilder.append(String.format("%s %s;\n", predicateUrlRegistry.get(key), key));
        }

        String formattedExpression = buildExpression(predicates);
        filterBuilder.append(formattedExpression);

        stringBuilder.append(String.format(WHERE_TEMPLATE, filterBuilder));
        stringBuilder.append(GROUP_BY_SUBJECT);

        return stringBuilder.toString();
    }

    private String buildExpression(List<RdfPredicate> predicates) {
        HashMap<String, List<String>> filters = new HashMap<>();

        for (RdfPredicate predicate : predicates) {
            if (!filters.containsKey(predicate.getVariable())) {
                filters.put(predicate.getVariable(), new ArrayList<>());
            }
            filters.get(predicate.getVariable()).add(getFilterForPredicate(predicate));
        }

        StringBuilder filterBuilder = new StringBuilder();
        for (List<String> valueFilters : filters.values()) {
            for (String filter : valueFilters) {
                filterBuilder.append(filter).append(" .\n");
            }
        }

        return filterBuilder.toString();
    }

    private String getFilterForPredicate(RdfPredicate predicate) {

        if(!predicateTypeRegistry.containsKey(predicate.getVariable())) {
            throw new BadLogicException(String.format("Nije podrzana pretraga po varijabli %s", predicate.getVariable()));
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_STRING) || predicateTypeRegistry.get(predicate.getVariable()).equals(LINK)) {
            return String.format("FILTER %s(lcase(str(%s)), lcase(str('%s')))", predicate.getOperator(), predicate.getVariable(), predicate.getValue());
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_INTEGER)) {
            return String.format("FILTER (?%s %s %s)", predicate.getVariable(), predicate.getOperator(), predicate.getValue());
        }

        if (predicateTypeRegistry.get(predicate.getVariable()).equals(XSD_DATE)) {
            return String.format("FILTER (?%s %s '%s'^^%s)", predicate.getVariable(), predicate.getOperator(), predicate.getValue(), XSD_DATE);
        }

        return "";
    }

}
