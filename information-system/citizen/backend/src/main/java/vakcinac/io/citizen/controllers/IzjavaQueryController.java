package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.service.IzjavaService;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "izjave/query", produces = { "application/xml" })
public class IzjavaQueryController {
	
	@Autowired
	private IzjavaService izjavaService;

	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin', 'Sluzbenik')")
	@GetMapping("/{id1}/{id2}")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(izjavaService.readPlain(id));
    	}
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type.equals("PDF") ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_XHTML_XML);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		return new ResponseEntity<Object>(izjavaService.readTransformed(id, type), headers, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin', 'Sluzbenik')")
	@GetMapping(path = "/{id1}/{id2}/links")
    public ResponseEntity<?> readLinks(@PathVariable String id1, @PathVariable String id2) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(izjavaService.readLinks(id));
    }
	
	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin', 'Sluzbenik')")
	@GetMapping(path = "/{id1}/{id2}/rdf")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(izjavaService.extractRdf(id, type));
    }
}
