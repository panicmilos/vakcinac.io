package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.service.PotvrdaService;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "potvrde/query", produces = { "application/xml" })
public class PotvrdaQueryController {
	
	@Autowired
    private PotvrdaService potvrdaService;
	
	@GetMapping("/{id}")
    public ResponseEntity<?> preview(@PathVariable String id, @RequestParam(required = false) String type) throws Exception {
    	
    	if (type == null) {
    		return ResponseEntity.ok(potvrdaService.readPlain(id));
    	}
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type.equals("PDF") ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_XHTML_XML);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		return new ResponseEntity<byte[]>(potvrdaService.readTransformed(id, type), headers, HttpStatus.OK);
    }
	
	 @GetMapping("/{id}/links")
    public ResponseEntity<?> readLinks(@PathVariable String id) throws Exception {
    	    	
    	return ResponseEntity.ok(potvrdaService.readLinks(id));
    }
    
    @GetMapping("/{id}/rdf")
    public ResponseEntity<?> extractRdf(@PathVariable String id, @RequestParam(required = false) String type) throws Exception {
    	    	
    	return ResponseEntity.ok(potvrdaService.extractRdf(id, type));
    }
   
}
