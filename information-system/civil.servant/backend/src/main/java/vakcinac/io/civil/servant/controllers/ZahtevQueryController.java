package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.civil.servant.service.ZahtevService;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "zahtevi/query", produces = { "application/xml" })
public class ZahtevQueryController {

	@Autowired
	private ZahtevService zahtevService;

	@GetMapping("/not-processed")
    public ResponseEntity<?> findAllNotProcesse() throws Exception {
    	
    	return ResponseEntity.ok(zahtevService.findAllNotProcessed());
    }
	
	@GetMapping("/{id1}/{id2}")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "_" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(zahtevService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(zahtevService.readTransformed(id, type));
    }
	
	@GetMapping(path = "/{id1}/{id2}/links")
    public ResponseEntity<?> readLinks(@PathVariable String id1, @PathVariable String id2) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(zahtevService.readLinks(id));
    }
	
    @GetMapping(path = "/{id1}/{id2}/rdf")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(zahtevService.extractRdf(id, type));
    }
}
