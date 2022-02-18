package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.service.SaglasnostService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSaglasnostRequest;

@Controller
@CrossOrigin("*")
@RequestMapping("/saglasnosti")
public class SaglasnostController extends ControllerBase {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public SaglasnostController(ModelMapper mapper, CitizenValidator validator) {
        super(mapper, validator);
    }
    
    @GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(saglasnostService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(saglasnostService.readPreview(id, type));
    }
    
    @GetMapping(path = "/{id1}/{id2}/rdf", produces = "text/plain")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(saglasnostService.extractRdf(id, type));
    }

    @PostMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> apply(@RequestBody CreateSaglasnostRequest createSaglasnostRequest) throws Exception {
        validate(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = mapper.map(createSaglasnostRequest, SaglasnostZaSprovodjenjePreporuceneImunizacije.class);

        SaglasnostZaSprovodjenjePreporuceneImunizacije createdSaglasnost = saglasnostService.create(saglasnost);

        return ResponseEntity.ok(createdSaglasnost);
    }
}
