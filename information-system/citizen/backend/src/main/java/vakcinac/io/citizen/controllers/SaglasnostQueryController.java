package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.service.SaglasnostService;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "saglasnosti/query", produces = { "application/xml" })
public class SaglasnostQueryController {

    @Autowired
    private SaglasnostService saglasnostService;

	@GetMapping("/{id1}/{id2}")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(saglasnostService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(saglasnostService.readTransformed(id, type));
    }
    
	@GetMapping(path = "/{id1}/{id2}/links")
    public ResponseEntity<?> readLinks(@PathVariable String id1, @PathVariable String id2) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(saglasnostService.readLinks(id));
    }

    @GetMapping(path = "/{id1}/{id2}/rdf", produces = "text/plain")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(saglasnostService.extractRdf(id, type));
    }
}
