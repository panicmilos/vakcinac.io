package vakcinac.io.civil.servant.service;

import org.apache.jena.query.QuerySolution;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.CloseableResultSet;
import vakcinac.io.core.requests.helpers.LogicalExpression;
import vakcinac.io.core.services.SearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    private final String IZJAVA_GRAPH_URI = "http://localhost:3030/CitizenDataset/data/izjava";
    private final String SAGLASNOST_GRAPH_URI = "http://localhost:3030/CitizenDataset/data/saglasnosti";
    private final String ZAHTEVI_GRAPH_URI = "http://localhost:3030/CitizenDataset/data/zahtevi";
    private final String IZVESTAJI_GRAPH_URI = "http://localhost:3030/CitizenDataset/data/izvestaji";

    //IZJAVA
    private HashMap<String, String> izjavaPredicateUrlRegistry;
    private HashMap<String, String> izjavaPredicateTypeRegistry;

    //SAGLASNOST
    private HashMap<String, String> saglasnostPredicateUrlRegistry;
    private HashMap<String, String> saglasnostPredicateTypeRegistry = new HashMap<>();

    public RdfSearchService(CivilServantJenaRepository jenaRepository) {
        super(jenaRepository);
    }

    @Override
    protected void initRegistry() {

        // IZJAVA
        izjavaPredicateUrlRegistry = new HashMap<>();
        izjavaPredicateTypeRegistry = new HashMap<>();

        izjavaPredicateUrlRegistry.put("?opstina", "<https://www.vakcinac-io.rs/rdfs/interesovanje/uOpstini>");
        izjavaPredicateUrlRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>");
        izjavaPredicateUrlRegistry.put("?zeljeneVakcine", "<https://www.vakcinac-io.rs/rdfs/interesovanje/zeljeneVakcine>");
        izjavaPredicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");

        izjavaPredicateTypeRegistry.put("?opstina", LINK);
        izjavaPredicateTypeRegistry.put("?za_interesovanje", LINK);
        izjavaPredicateTypeRegistry.put("?zeljeneVakcine", LINK);
        izjavaPredicateTypeRegistry.put("?izdat", XSD_DATE);

        // SAGLASNOST
        saglasnostPredicateUrlRegistry = new HashMap<>();
        saglasnostPredicateTypeRegistry = new HashMap<>();

        saglasnostPredicateUrlRegistry.put("?naOsnovuInteresovanja", "<https://www.vakcinac-io.rs/rdfs/saglasnost/naOsnovuInteresovanja>");
        saglasnostPredicateUrlRegistry.put("?vakcinisanOd", "<https://www.vakcinac-io.rs/rdfs/saglasnost/vakcinisanOd>");
        saglasnostPredicateUrlRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/saglasnost/za>");
        saglasnostPredicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
        saglasnostPredicateUrlRegistry.put("?izmenjen", "<https://www.vakcinac-io.rs/rdfs/deljeno/izmenjen>");

        saglasnostPredicateTypeRegistry.put("?naOsnovuInteresovanja", LINK);
        saglasnostPredicateTypeRegistry.put("?vakcinisanOd", LINK);
        saglasnostPredicateTypeRegistry.put("?za", LINK);
        saglasnostPredicateTypeRegistry.put("?izdat", XSD_DATE);
        saglasnostPredicateTypeRegistry.put("?izmenjen", XSD_DATE);

    }

    @Override
    public List<String> search(String graph, LogicalExpression expression) {

        switch (graph) {
            case "izjava": {
                graph = IZJAVA_GRAPH_URI;
                this.predicateUrlRegistry = izjavaPredicateUrlRegistry;
                this.predicateTypeRegistry = izjavaPredicateTypeRegistry;
                break;
            }
            case "saglasnosti": {
                graph = SAGLASNOST_GRAPH_URI;
                this.predicateUrlRegistry = saglasnostPredicateUrlRegistry;
                this.predicateTypeRegistry = saglasnostPredicateTypeRegistry;
                break;
            }
            case "zahtevi": {
                graph = ZAHTEVI_GRAPH_URI;
                break;
            }
            case "izvestaj": {
                graph = IZVESTAJI_GRAPH_URI;
                break;
            }
            default:
                throw new BadLogicException("");

        }

        String sparqlQuery = formatQuery(graph, expression);

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
