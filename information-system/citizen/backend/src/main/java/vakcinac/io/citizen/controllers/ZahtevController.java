package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.citizen.service.ZahtevService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateZahtevRequest;

@Controller
@RequestMapping("zahtevi")
public class ZahtevController extends ControllerBase {
	
	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	public ZahtevController(ModelMapper mapper, CitizenValidator validator) {
		super(mapper, validator);
	}
	
	@GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
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

	@PostMapping
	public ResponseEntity<ZahtevZaIzdavanjeZelenogSertifikata> apply(@RequestBody CreateZahtevRequest createZahtevRequest) throws Exception {
		validate(createZahtevRequest);

		ZahtevZaIzdavanjeZelenogSertifikata zahtev = mapper.map(createZahtevRequest, ZahtevZaIzdavanjeZelenogSertifikata.class);
		
		ZahtevZaIzdavanjeZelenogSertifikata createdZahtev = zahtevService.create(zahtev);
		
		return ResponseEntity.ok(createdZahtev);
	}
}
