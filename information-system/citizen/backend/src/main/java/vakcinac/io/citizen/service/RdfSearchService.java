package vakcinac.io.citizen.service;

import org.apache.jena.query.QuerySolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.repository.jena.CloseableResultSet;
import vakcinac.io.core.requests.helpers.LogicalExpression;
import vakcinac.io.core.results.doc.QueryDocumentsResult;
import vakcinac.io.core.services.SearchService;
import vakcinac.io.core.utils.JenaAuthenticationUtils;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    JenaAuthenticationUtils.JenaConnectionProperties jenaProperties = JenaAuthenticationUtils.loadProperties();

    private final String POTVRDA_GRAPH_URI = String.format("%s/potvrda", jenaProperties.dataEndpoint);
    private final String DIGITALNI_SERTIFIKAT_GRAPH_URI = String.format("%s/digitalni-sertifikat", jenaProperties.dataEndpoint);

    // POTVRDA
    private HashMap<String, String> potvrdaPredicateUrlRegistry;
    private HashMap<String, String> potvrdaPredicateTypeRegistry;

    // POTVRDA
    private HashMap<String, String> digitalniSertifikatPredicateUrlRegistry;
    private HashMap<String, String> digitalniSertifikatPredicateTypeRegistry;

    public RdfSearchService(CitizenJenaRepository jenaRepository) throws IOException {
        super(jenaRepository);
    }

    @Override
    protected void initRegistry() {

        // POTVRDA
        potvrdaPredicateUrlRegistry = new HashMap<>();
        potvrdaPredicateTypeRegistry = new HashMap<>();

        potvrdaPredicateUrlRegistry.put("?naOsnovuInteresovanja", "<https://www.vakcinac-io.rs/rdfs/potvrda/naOsnovuInteresovanja>");
        potvrdaPredicateUrlRegistry.put("?saSaglasnoscu", "<https://www.vakcinac-io.rs/rdfs/potvrda/saSaglasnoscu>");
        potvrdaPredicateUrlRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/potvrda/za>");
        potvrdaPredicateUrlRegistry.put("?brojDoza", "<https://www.vakcinac-io.rs/rdfs/potvrda/brojDoza>");
        potvrdaPredicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
        potvrdaPredicateUrlRegistry.put("?izmenjen", "<https://www.vakcinac-io.rs/rdfs/potvrda/izmenjen>");
        potvrdaPredicateUrlRegistry.put("?izdao", "<https://www.vakcinac-io.rs/rdfs/potvrda/izdao>");

        potvrdaPredicateTypeRegistry.put("?naOsnovuInteresovanja", LINK);
        potvrdaPredicateTypeRegistry.put("?saSaglasnoscu", LINK);
        potvrdaPredicateTypeRegistry.put("?za", LINK);
        potvrdaPredicateTypeRegistry.put("?brojDoza", XSD_INTEGER);
        potvrdaPredicateTypeRegistry.put("?izdat", XSD_DATE);
        potvrdaPredicateTypeRegistry.put("?izmenjen", XSD_DATE);
        potvrdaPredicateTypeRegistry.put("?izdao", LINK);

        // DIGITALNI SERTIFIKAT
        digitalniSertifikatPredicateUrlRegistry = new HashMap<>();
        digitalniSertifikatPredicateTypeRegistry = new HashMap<>();

        digitalniSertifikatPredicateUrlRegistry.put("?saPotvrdom", "<https://www.vakcinac-io.rs/rdfs/digitalni-sertifikat/saPotvrdom>");
        digitalniSertifikatPredicateUrlRegistry.put("?naOsnovuZahteva", "<https://www.vakcinac-io.rs/rdfs/digitalni-sertifikat/naOsnovuZahteva>");
        digitalniSertifikatPredicateUrlRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/potvrda/za>");
        digitalniSertifikatPredicateUrlRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
        digitalniSertifikatPredicateUrlRegistry.put("?izmenjen", "<https://www.vakcinac-io.rs/rdfs/potvrda/izmenjen>");
        digitalniSertifikatPredicateUrlRegistry.put("?izdao", "<https://www.vakcinac-io.rs/rdfs/digitalni-sertifikat/izdao>");

        digitalniSertifikatPredicateTypeRegistry.put("?saPotvrdom", LINK);
        digitalniSertifikatPredicateTypeRegistry.put("?naOsnovuZahteva", LINK);
        digitalniSertifikatPredicateTypeRegistry.put("?za", LINK);
        digitalniSertifikatPredicateTypeRegistry.put("?izdat", XSD_DATE);
        digitalniSertifikatPredicateTypeRegistry.put("?izmenjen", XSD_DATE);
        digitalniSertifikatPredicateTypeRegistry.put("?izdao", LINK);
    }

    @Override
    public QueryDocumentsResult search(String graph, LogicalExpression expression) {
        switch (graph) {
            case "potvrda": {
                graph = POTVRDA_GRAPH_URI;
                this.predicateUrlRegistry = potvrdaPredicateUrlRegistry;
                this.predicateTypeRegistry = potvrdaPredicateTypeRegistry;
                break;
            }
            case "sertifikat": {
                graph = DIGITALNI_SERTIFIKAT_GRAPH_URI;
                this.predicateUrlRegistry = digitalniSertifikatPredicateUrlRegistry;
                this.predicateTypeRegistry = digitalniSertifikatPredicateTypeRegistry;
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
                document.setValue(querySolution.get("?s").toString());
                document.setCreatedAt(querySolution.get("?izdat").toString());

                queryDocumentsResult.getDocument().add(document);
            }
        }

        return queryDocumentsResult;
    }

}
