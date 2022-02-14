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
import vakcinac.io.citizen.service.GradjaninService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/gradjani")
public class GradjaniController extends ControllerBase {
	
	private GradjaninService gradjaninService;
	
	@Autowired
	public GradjaniController(ModelMapper mapper, CitizenValidator validator, GradjaninService gradjaninService) {
		super(mapper, validator);
		this.gradjaninService = gradjaninService;
	}
	
	@PostMapping("/domaci")
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody CreateDomaciGradjaninRequest createDomaciGradjaninRequest) {
		validate(createDomaciGradjaninRequest);
		
		DomaciGradjanin domaciGradjanin = (DomaciGradjanin) map(createDomaciGradjaninRequest, DomaciGradjanin.class);
		
		DomaciGradjanin createdDomaciGradjanin = (DomaciGradjanin) gradjaninService.create(domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}
	
	@PostMapping("/strani")
	public ResponseEntity<StraniGradjanin> createStraniGradjanin(@RequestBody CreateStraniGradjaninRequest createStraniGradjaninRequest) {
		validate(createStraniGradjaninRequest);
		
		StraniGradjanin straniGradjanin = (StraniGradjanin) map(createStraniGradjaninRequest, StraniGradjanin.class);
		
		StraniGradjanin createdStraniGradjanin = (StraniGradjanin) gradjaninService.create(straniGradjanin);
		
		return ResponseEntity.ok(createdStraniGradjanin);
	}
}
