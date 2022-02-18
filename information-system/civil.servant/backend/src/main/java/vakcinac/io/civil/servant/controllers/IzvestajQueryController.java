package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.civil.servant.service.IzvestajService;

@Controller
@RequestMapping(path = "izvestaji/query", produces = { "application/xml" })
public class IzvestajQueryController {

	@Autowired
	private IzvestajService izvestajService;

	@GetMapping("/{id}")
    public ResponseEntity<?> preview(@PathVariable String id, @RequestParam(required = false) String type) throws Exception {
		
    	if (type == null) {
    		return ResponseEntity.ok(izvestajService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(izvestajService.readPreview(id, type));
    }

	@GetMapping(path = "/{id}/rdf", produces = "text/plain")
    public ResponseEntity<?> extractRdf(@PathVariable String id, @RequestParam(required = false) String type) throws Exception {

    	return ResponseEntity.ok(izvestajService.extractRdf(id, type));
    }
}
