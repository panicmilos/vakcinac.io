package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.requests.CreateDomaciGradjaninRequest;
import vakcinac.io.citizen.requests.CreateStraniGradjaninRequest;
import vakcinac.io.citizen.service.DomaciGradjaninService;
import vakcinac.io.citizen.service.StraniGradjaninService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/gradjani")
public class GradjaniController extends ControllerBase {
	
	private DomaciGradjaninService domaciGradjaninService;
	private StraniGradjaninService straniGradjaninService;
	
	@Autowired
	public GradjaniController(ModelMapper mapper, CitizenValidator validator, DomaciGradjaninService domaciGradjaninService, StraniGradjaninService straniGradjaninService) {
		super(mapper, validator);
		this.domaciGradjaninService = domaciGradjaninService;
		this.straniGradjaninService = straniGradjaninService;
	}
	
	@PostMapping("/domaci")
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody CreateDomaciGradjaninRequest createDomaciGradjaninRequest) {
		validate(createDomaciGradjaninRequest);
		
		DomaciGradjanin domaciGradjanin = (DomaciGradjanin) map(createDomaciGradjaninRequest, DomaciGradjanin.class);
		
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(domaciGradjanin.getKorisnickoIme(), domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}
	
	@PostMapping("/strani")
	public ResponseEntity<StraniGradjanin> createStraniGradjanin(@RequestBody CreateStraniGradjaninRequest createStraniGradjaninRequest) {
		validate(createStraniGradjaninRequest);
		
		StraniGradjanin straniGradjanin = (StraniGradjanin) map(createStraniGradjaninRequest, StraniGradjanin.class);
		
		StraniGradjanin createdStraniGradjanin = straniGradjaninService.create(straniGradjanin.getKorisnickoIme(), straniGradjanin);
		
		return ResponseEntity.ok(createdStraniGradjanin);
	}
}
