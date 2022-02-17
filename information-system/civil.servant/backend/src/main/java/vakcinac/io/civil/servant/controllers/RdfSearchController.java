package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.civil.servant.service.RdfSearchService;
import vakcinac.io.core.requests.RdfSearchRequest;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("meta-search")
public class RdfSearchController {

    RdfSearchService rdfSearchService;

    @Autowired
    public RdfSearchController(RdfSearchService rdfSearchService) {
        this.rdfSearchService = rdfSearchService;
    }

    @PostMapping
    public ResponseEntity<List<String>> search(RdfSearchRequest request) {
        System.out.println(request.getPredicates());
        System.out.println(request.getExpression());
        System.out.println(request.getGraph());
        return ResponseEntity.ok(rdfSearchService.search(request.getGraph(), request.getExpression(), new HashMap<>()));
    }

}
