package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.request.CreateDomaciGradjanin;
import vakcinac.io.citizen.service.DomaciGradjaninService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/gradjani")
public class DomaciGradjaniController extends ControllerBase {
	
	private DomaciGradjaninService domaciGradjaninService;
	
	@Autowired
	public DomaciGradjaniController(ModelMapper mapper, CitizenValidator validator, DomaciGradjaninService domaciGradjaninService) {
		super(mapper, validator);
		this.domaciGradjaninService = domaciGradjaninService;
	}
	
	@PostMapping
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody CreateDomaciGradjanin domaciGradjaninRequest) {
		validate(domaciGradjaninRequest);
		
		DomaciGradjanin gradjanin = (DomaciGradjanin) map(domaciGradjaninRequest, DomaciGradjanin.class);
		
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(gradjanin.getKorisnickoIme(), gradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}

}
