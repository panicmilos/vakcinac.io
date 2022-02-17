package vakcinac.io.core.services;

import java.util.*;

public abstract class SearchService {

    protected HashMap<String, String> predicateTypeRegistry = new HashMap<>();

    protected static final String PREFIXES = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
    protected static final String SELECT_TEMPLATE = "SELECT DISTINCT * FROM <%s>\n";
    protected static final String WHERE_TEMPLATE = "WHERE { %s }\n";
    protected static final String GROUP_BY_SUBJECT = "GROUP BY ?s";
    protected static final String GROUP_BY_PREDICATES = "GROUP BY ?p";

    // types
    protected static final String XSD_DATE = "xsd:date";
    protected static final String XSD_INTEGER = "xsd:integer";
    protected static final String XSD_STRING = "xsd:string";
    protected static final String LINK = "link";

    protected abstract void initRegistry();
    protected abstract List<String> search(String graph, String expression, Map<String, String> predicateMap);

    protected String formatQuery(String graph, String expression, Map<String, String> predicateMap) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIXES);
        stringBuilder.append(String.format(SELECT_TEMPLATE, graph));

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append("?s\n");

        for(String key : predicateMap.keySet()) {
            filterBuilder.append(String.format("%s %s", key, predicateMap.get(key)));
        }

        String formattedExpression = parseExpression(expression, predicateMap);
        filterBuilder.append(formattedExpression);

        stringBuilder.append(String.format(WHERE_TEMPLATE, filterBuilder.toString()));
        stringBuilder.append(GROUP_BY_SUBJECT);

        return stringBuilder.toString();
    }

    private String parseExpression(String expression, Map<String, String> predicateMap) {
        HashMap<String, List<String>> filters = new HashMap<>();

        String[] lines = expression.split("\\n");
        String currentOperator = "";

        for (String line : lines) {

            if (line.length() == 1) {
                currentOperator = line;
                continue;
            }

            List<String> lineParts = parseLine(line);
            String filter = getFilterForLine(lineParts);

            String variable = lineParts.get(0);

            if (predicateMap.containsKey(variable)) {
                filters.get(variable).add(filter);
            } else {
                filters.put(variable, new ArrayList<>());
            }

        }

        StringBuilder filterBuilder = new StringBuilder();
        for (List<String> valueFilters : filters.values()) {
            filterBuilder.append(String.join(".\n", valueFilters));
        }

        return filterBuilder.toString();
    }

    private String getFilterForLine(List<String> lineParts) {

        String variable = lineParts.get(0);
        String operator = lineParts.get(1);
        String value = lineParts.get(2);

        if (predicateTypeRegistry.get(variable).equals(XSD_STRING) || predicateTypeRegistry.get(variable).equals(LINK)) {
            return String.format("FILTER contains(lcase(str(%s)), lcase(str('%s')))", operator, value);
        }

        return "";
    }

    private List<String> parseLine(String line) {

        String[] partsArray = line.split(" ");
        List<String> parts = Arrays.asList(partsArray);
        int containsIndex = parts.indexOf("contains");

        if (containsIndex != -1) {
            String subString = String.join(" ", parts.subList(containsIndex, parts.size()));
            List<String> subStringParts = parts.subList(0, containsIndex);
            subStringParts.add(subString);
            return subStringParts;
        }

        return parts;
    }

}
