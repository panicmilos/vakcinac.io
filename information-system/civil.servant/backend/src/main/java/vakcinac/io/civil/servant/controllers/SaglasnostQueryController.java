package vakcinac.io.civil.servant.controllers;

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

import vakcinac.io.civil.servant.service.SaglasnostService;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/saglasnosti/query", produces = { "application/xml" })
public class SaglasnostQueryController {

    @Autowired
    private SaglasnostService saglasnostService;

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "_" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(saglasnostService.readPlain(id));
    	}
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type.equals("PDF") ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_XHTML_XML);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		return new ResponseEntity<byte[]>(saglasnostService.readTransformed(id, type), headers, HttpStatus.OK);
    }
    
    @GetMapping(path = "/{id1}/{id2}/links")
    public ResponseEntity<?> readLinks(@PathVariable String id1, @PathVariable String id2) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(saglasnostService.readLinks(id));
    }
    
    @GetMapping(path = "/{id1}/{id2}/rdf")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(saglasnostService.extractRdf(id, type));
    }
    
}
