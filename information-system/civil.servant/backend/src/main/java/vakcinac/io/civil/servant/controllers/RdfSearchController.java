package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.civil.servant.service.RdfSearchService;
import vakcinac.io.core.requests.MetaSearchRequest;
import vakcinac.io.core.results.doc.QueryDocumentsResult;

@Controller
@RequestMapping("meta-search")
public class RdfSearchController {

    RdfSearchService rdfSearchService;

    @Autowired
    public RdfSearchController(RdfSearchService rdfSearchService) {
        this.rdfSearchService = rdfSearchService;
    }

    @PostMapping
    public ResponseEntity<QueryDocumentsResult> search(@RequestBody MetaSearchRequest request) {
        QueryDocumentsResult searchResult = rdfSearchService.search(request.getGraph(), request.getExpression());
        return ResponseEntity.ok(searchResult);
    }

}
