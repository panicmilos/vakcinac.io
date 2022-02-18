package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.factories.ZahtevZaIzdavanjeZelenogSertifikataFactory;
import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.civil.servant.service.ZahtevService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateZahtevRequest;

@Controller
@RequestMapping(path = "zahtevi")
public class ZahtevController extends ControllerBase {

	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	public ZahtevController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
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
