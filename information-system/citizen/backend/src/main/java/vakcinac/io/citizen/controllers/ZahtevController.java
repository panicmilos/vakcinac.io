package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@PostMapping
	public ResponseEntity<ZahtevZaIzdavanjeZelenogSertifikata> apply(@RequestBody CreateZahtevRequest createZahtevRequest) throws Exception {
		
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = mapper.map(createZahtevRequest, ZahtevZaIzdavanjeZelenogSertifikata.class);
		
		ZahtevZaIzdavanjeZelenogSertifikata createdZahtev = zahtevService.create(zahtev);
		
		return ResponseEntity.ok(createdZahtev);
	}
}