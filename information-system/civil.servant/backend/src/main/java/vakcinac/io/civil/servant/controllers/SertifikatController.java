package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.civil.servant.models.dig.DeclineZahtev;
import vakcinac.io.civil.servant.models.dig.DigitalniSertifikat;
import vakcinac.io.civil.servant.service.SertifikatService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.civil.servant.requests.DeclineZahtevRequest;

@Controller
@RequestMapping("/sertifikati")
public class SertifikatController extends ControllerBase {
	
	private SertifikatService sertifikatService;
	
	@Autowired
	public SertifikatController(ModelMapper mapper, CivilServantValidator validator, SertifikatService sertifikatService) {
		super(mapper, validator);
		this.sertifikatService = sertifikatService;
	}
	
	@GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(sertifikatService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(sertifikatService.readPreview(id, type));
    }
	
	@GetMapping(path = "/{id1}/{id2}/rdf", produces = "text/plain")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(sertifikatService.extractRdf(id, type));
    }
	
    @PreAuthorize("hasAnyRole('Sluzbenik')")
	@PostMapping("/approve")
	public ResponseEntity<DigitalniSertifikat> approve(@RequestBody CreateSertifikatRequest createSertifikatRequest) throws Exception {
		validate(createSertifikatRequest);
    	
		DigitalniSertifikat sertifikat = mapper.map(createSertifikatRequest, DigitalniSertifikat.class);
		
		DigitalniSertifikat createdSertifikat = sertifikatService.create(sertifikat);
		
		return ResponseEntity.ok(createdSertifikat);
	}
    
    @PreAuthorize("hasAnyRole('Sluzbenik')")
 	@PostMapping("/decline")
 	public ResponseEntity<DeclineZahtev> approve(@RequestBody DeclineZahtevRequest declineZahtevRequest) throws Exception {
 		validate(declineZahtevRequest);
    	
    	DeclineZahtev decline = mapper.map(declineZahtevRequest, DeclineZahtev.class);
 		
    	DeclineZahtev declinedZahtev = sertifikatService.decline(decline);
 		
 		return ResponseEntity.ok(declinedZahtev);
 	}
}
