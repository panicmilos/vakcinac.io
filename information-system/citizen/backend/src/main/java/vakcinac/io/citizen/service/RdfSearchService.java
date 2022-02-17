package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.requests.helpers.LogicalExpression;
import vakcinac.io.core.services.SearchService;

import java.util.List;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    private CitizenJenaRepository jenaRepository;

    @Autowired
    public RdfSearchService(CitizenJenaRepository jenaRepository) {
        super(jenaRepository);
    }

    @Override
    protected void initRegistry() {
        predicateTypeRegistry.put("?opstina", "<https://www.vakcinac-io.rs/rdfs/interesovanje/uOpstini>");
        predicateTypeRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>");
        predicateTypeRegistry.put("?zeljeneVakcine", "<https://www.vakcinac-io.rs/rdfs/interesovanje/zeljeneVakcine>");
        predicateTypeRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
    }

    @Override
    protected List<String> search(String graph, LogicalExpression predicates) {
        return null;
    }

}
