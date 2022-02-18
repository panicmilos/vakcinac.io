package vakcinac.io.civil.servant.service;

import org.apache.jena.query.QuerySolution;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.CloseableResultSet;
import vakcinac.io.core.requests.helpers.LogicalExpression;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.services.SearchService;

import java.util.HashMap;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    private final String IZJAVA_GRAPH_URI = "http://localhost:3030/ServantDataset/data/izjava";
    private final String SAGLASNOST_GRAPH_URI = "http://localhost:3030/ServantDataset/data/saglasnosti";
    private final String ZAHTEVI_GRAPH_URI = "http://localhost:3030/ServantDataset/data/zahtevi";
    private final String IZVESTAJI_GRAPH_URI = "http://localhost:3030/ServantDataset/data/izvestaji";

    //IZJAVA
    private HashMap<String, String> izjavaPredicateUrlRegistry;
    private HashMap<String, String> izjavaPredicateTypeRegistry;

    //SAGLASNOST
    private HashMap<String, String> saglasnostPredicateUrlRegistry;
    private HashMap<String, String> saglasnostPredicateTypeRegistry;

    //SAGLASNOST
    private HashMap<String, String> zahteviPredicateUrlRegistry;
    private HashMap<String, String> zahteviPredicateTypeRegistry;

    //IZVESTAJI
    private HashMap<String, String> izvestajiPredicateUrlRegistry;
    private HashMap<String, String> izvestajiPredicateTypeRegistry;

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
        izjavaPredicateTypeRegistry.put("?za", LINK);
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

        // SAGLASNOST
        zahteviPredicateUrlRegistry = new HashMap<>();
        zahteviPredicateTypeRegistry = new HashMap<>();

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

        // IZVESTAJI
        izvestajiPredicateUrlRegistry = new HashMap<>();
        izvestajiPredicateTypeRegistry = new HashMap<>();

        izvestajiPredicateUrlRegistry.put("?periodOd", "<https://www.vakcinac-io.rs/rdfs/izvestaj/periodDo>");
        izvestajiPredicateUrlRegistry.put("?periodDo", "<https://www.vakcinac-io.rs/rdfs/izvestaj/periodOd>");
        izvestajiPredicateUrlRegistry.put("?ukupnoDoza", "<https://www.vakcinac-io.rs/rdfs/izvestaj/ukupnoDoza>");
        izvestajiPredicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
        izvestajiPredicateUrlRegistry.put("?izdao", "<https://www.vakcinac-io.rs/rdfs/izvestaj/izdao>");

        izvestajiPredicateTypeRegistry.put("?periodOd", XSD_DATE);
        izvestajiPredicateTypeRegistry.put("?periodDo", XSD_DATE);
        izvestajiPredicateTypeRegistry.put("?ukupnoDOza", XSD_INTEGER);
        izvestajiPredicateTypeRegistry.put("?izdat", XSD_DATE);
        izvestajiPredicateTypeRegistry.put("?izdao", LINK);

    }

    @Override
    public QueryDocumentsResult search(String graph, LogicalExpression expression) {

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
                this.predicateUrlRegistry = zahteviPredicateUrlRegistry;
                this.predicateTypeRegistry = zahteviPredicateTypeRegistry;
                break;
            }
            case "izvestaj": {
                graph = IZVESTAJI_GRAPH_URI;
                this.predicateUrlRegistry = izjavaPredicateUrlRegistry;
                this.predicateTypeRegistry = izjavaPredicateTypeRegistry;
                break;
            }
            default:
                throw new BadLogicException("Dati graf ne postoji");

        }

        String sparqlQuery = formatQuery(graph, expression);

        System.out.println(sparqlQuery);

        QueryDocumentsResult queryDocumentsResult = new QueryDocumentsResult();

        try(CloseableResultSet set = jenaRepository.execQuery(sparqlQuery)) {
            while (set.hasNext()) {
                QuerySolution querySolution = set.next();

                QueryDocumentsResult.Document document = new QueryDocumentsResult.Document();
                document.setUrl(querySolution.get("?s").toString());
                document.setCreatedAt(querySolution.get("?izdat").toString());

                queryDocumentsResult.getDocument().add(document);
            }
        }

        return queryDocumentsResult;
    }

}
