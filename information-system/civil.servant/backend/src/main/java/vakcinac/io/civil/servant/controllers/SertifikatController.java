package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.models.dig.DeclineZahtev;
import vakcinac.io.civil.servant.models.dig.DigitalniSertifikat;
import vakcinac.io.civil.servant.requests.DeclineZahtevRequest;
import vakcinac.io.civil.servant.service.SertifikatService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSertifikatRequest;

@Controller
@CrossOrigin("*")
@RequestMapping("/sertifikati")
public class SertifikatController extends ControllerBase {
	
	private SertifikatService sertifikatService;
	
	@Autowired
	public SertifikatController(ModelMapper mapper, CivilServantValidator validator, SertifikatService sertifikatService) {
		super(mapper, validator);
		this.sertifikatService = sertifikatService;
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
 	public ResponseEntity<DeclineZahtev> decline(@RequestBody DeclineZahtevRequest declineZahtevRequest) throws Exception {
 		validate(declineZahtevRequest);
    	
    	DeclineZahtev decline = mapper.map(declineZahtevRequest, DeclineZahtev.class);
 		
    	DeclineZahtev declinedZahtev = sertifikatService.decline(decline);
 		
 		return ResponseEntity.ok(declinedZahtev);
 	}
}
