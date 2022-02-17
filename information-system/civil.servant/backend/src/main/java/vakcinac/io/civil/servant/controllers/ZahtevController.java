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

import vakcinac.io.civil.servant.factories.ZahtevZaIzdavanjeZelenogSertifikataFactory;
import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.civil.servant.service.ZahtevService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateZahtevRequest;

@Controller
@RequestMapping(path = "zahtevi", produces = { "application/xml" })
public class ZahtevController extends ControllerBase {

	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	public ZahtevController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}

	@GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "_" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(zahtevService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(zahtevService.readPreview(id, type));
    }
	
    @GetMapping(path = "/{id1}/{id2}/rdf", produces = "text/plain")
    public ResponseEntity<?> extractRdf(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;

    	return ResponseEntity.ok(zahtevService.extractRdf(id, type));
    }

    
	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin')")
	@PostMapping
	public ResponseEntity<ZahtevZaIzdavanjeZelenogSertifikata> apply(@RequestBody CreateZahtevRequest createZahtevRequest) throws Exception {
		validate(createZahtevRequest);

		ZahtevZaIzdavanjeZelenogSertifikata zahtev = ZahtevZaIzdavanjeZelenogSertifikataFactory.create(createZahtevRequest);

		ZahtevZaIzdavanjeZelenogSertifikata createdZahtev = zahtevService.create(zahtev);
		
		return ResponseEntity.ok(createdZahtev);
	}
}
