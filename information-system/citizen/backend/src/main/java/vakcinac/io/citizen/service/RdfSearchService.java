package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vakcinac.io.core.services.SearchService;

import java.util.List;
import java.util.Map;

@Service
@RequestScope
public class RdfSearchService extends SearchService {

    @Override
    protected void initRegistry() {
        predicateTypeRegistry.put("?opstina", "<https://www.vakcinac-io.rs/rdfs/interesovanje/uOpstini>");
        predicateTypeRegistry.put("?za", "<https://www.vakcinac-io.rs/rdfs/interesovanje/za>");
        predicateTypeRegistry.put("?zeljeneVakcine", "<https://www.vakcinac-io.rs/rdfs/interesovanje/zeljeneVakcine>");
        predicateTypeRegistry.put("?izdat", "<https://www.vakcinac-io.rs/rdfs/deljeno/izdat>");
    }

    @Override
    protected List<String> search(String graph, String expression, Map<String, String> predicateMap) {
        return null;
    }

}
