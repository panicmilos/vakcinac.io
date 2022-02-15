package vakcinac.io.citizen.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.factories.DigitalniSertifikatFactory;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.service.SertifikatService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSertifikatRequest;

@Controller
@RequestMapping("/sertifikati")
public class SertifikatController extends ControllerBase {
	
	private SertifikatService sertifikatService;
	
	@Autowired
	public SertifikatController(ModelMapper mapper, CitizenValidator validator, SertifikatService sertifikatService) {
		super(mapper, validator);
		
		this.sertifikatService = sertifikatService;
	}
	
	@PostMapping
	public ResponseEntity<DigitalniSertifikat> apply(@RequestBody CreateSertifikatRequest createSertifikatRequest) throws XMLDBException, IOException {
		
		DigitalniSertifikat sertifikat = DigitalniSertifikatFactory.create(createSertifikatRequest);
		
		DigitalniSertifikat createdSertifikat = sertifikatService.create(sertifikat);
		
		return ResponseEntity.ok(createdSertifikat);
	}
}
