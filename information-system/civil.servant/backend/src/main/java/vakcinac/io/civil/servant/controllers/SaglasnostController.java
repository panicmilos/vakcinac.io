package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.civil.servant.factories.SaglasnostZaSprovodjenjePreporuceneImunizacijeFactory;
import vakcinac.io.civil.servant.models.sag.AzurirajSaglasnost;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.service.SaglasnostService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSaglasnostRequest;
import vakcinac.io.core.requests.UpdateSaglasnostRequest;

@Controller
@RequestMapping(path = "/saglasnosti", produces = { "application/xml" })
public class SaglasnostController extends ControllerBase {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public SaglasnostController(ModelMapper mapper, CivilServantValidator validator) {
        super(mapper, validator);
    }

    @GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "_" + id2;
		
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
    
//    @PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin')")
    @PostMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> apply(@RequestBody CreateSaglasnostRequest createSaglasnostRequest) throws Exception {
        validate(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = SaglasnostZaSprovodjenjePreporuceneImunizacijeFactory.create(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije createdSaglasnost = saglasnostService.create(saglasnost);

        return ResponseEntity.ok(createdSaglasnost);
    }

    @PutMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> update(@RequestBody UpdateSaglasnostRequest updateSaglasnostRequest) throws Exception {
        validate(updateSaglasnostRequest);

        AzurirajSaglasnost noviPodaci = mapper.map(updateSaglasnostRequest, AzurirajSaglasnost.class);
        SaglasnostZaSprovodjenjePreporuceneImunizacije updatedSaglasnost = saglasnostService.update(noviPodaci);

        return ResponseEntity.ok(updatedSaglasnost);
    }
    
    @GetMapping("/za")
	public ResponseEntity<String> getSaglasnostZa(@RequestParam String za) {
		return ResponseEntity.ok(saglasnostService.getSaglasnostZa(za));
	}
}
