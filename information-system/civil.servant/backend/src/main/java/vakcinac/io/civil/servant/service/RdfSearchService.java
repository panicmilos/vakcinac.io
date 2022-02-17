package vakcinac.io.civil.servant.service;

import org.apache.jena.query.QuerySolution;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.repository.jena.CloseableResultSet;
import vakcinac.io.core.requests.helpers.RdfPredicate;
import vakcinac.io.core.services.SearchService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    private final String IZJAVA_GRAPH_URI = "http://localhost:3030/CitizenDataset/data/izjava";

    public RdfSearchService(CivilServantJenaRepository jenaRepository) {
        super(jenaRepository);
    }

    @Override
    protected void initRegistry() {
        predicateUrlRegistry.put("?opstina", "<https://www.vakcinac-io.rs/rdfs/interesovanje/uOpstini>");
        predicateUrlRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>");
        predicateUrlRegistry.put("?zeljeneVakcine", "<https://www.vakcinac-io.rs/rdfs/interesovanje/zeljeneVakcine>");
        predicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");

        predicateTypeRegistry.put("?opstina", LINK);
        predicateTypeRegistry.put("?za", LINK);
        predicateTypeRegistry.put("?zeljeneVakcine", LINK);
        predicateTypeRegistry.put("?izdat", XSD_DATE);
    }

    @Override
    public List<String> search(String graph, List<RdfPredicate> predicates) {

        if (graph.equals("izjava")) {
            graph = IZJAVA_GRAPH_URI;
        }

        String sparqlQuery = formatQuery(graph, predicates);

        System.out.println(sparqlQuery);

        List<String> searchResults = new ArrayList<>();

        try(CloseableResultSet set = jenaRepository.execQuery(sparqlQuery)) {
            while (set.hasNext()) {
                QuerySolution querySolution = set.next();

                searchResults.add(querySolution.get("?s").toString());
            }
        }

        return searchResults;
    }

}
