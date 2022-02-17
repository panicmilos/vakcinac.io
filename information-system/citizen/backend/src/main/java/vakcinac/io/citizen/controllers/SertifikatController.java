package vakcinac.io.citizen.controllers;

import java.io.IOException;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.factories.DigitalniSertifikatFactory;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.service.SertifikatService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.core.responses.CountResponse;

@Controller
@RequestMapping("/sertifikati")
public class SertifikatController extends ControllerBase {
	
	private SertifikatService sertifikatService;
	
	@Autowired
	public SertifikatController(ModelMapper mapper, CitizenValidator validator, SertifikatService sertifikatService) {
		super(mapper, validator);
		
		this.sertifikatService = sertifikatService;
	}
	
	@GetMapping(path = "/count")
	public ResponseEntity<CountResponse> count(@RequestParam(name="startDate") String startDateS, @RequestParam(name="endDate") String endDateS) throws IOException {
		
		LocalDate startDate = LocalDate.parse(startDateS);
		LocalDate endDate = LocalDate.parse(endDateS);
		
		int numOfSertifikat = sertifikatService.count("/sertifikati", startDate, endDate);
		
		return ResponseEntity.ok(new CountResponse(numOfSertifikat));
	}
	
	@PostMapping
	public ResponseEntity<DigitalniSertifikat> approve(@RequestBody CreateSertifikatRequest createSertifikatRequest) throws XMLDBException, IOException {
		
		DigitalniSertifikat sertifikat = DigitalniSertifikatFactory.create(createSertifikatRequest);
		
		DigitalniSertifikat createdSertifikat = sertifikatService.create(sertifikat);
		
		return ResponseEntity.ok(createdSertifikat);
	}
}
