package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fluentvalidator.context.ValidationResult;
import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.dgradj.TdomaciGradjanin;
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
		
		TdomaciGradjanin model = (TdomaciGradjanin) map(domaciGradjaninRequest, TdomaciGradjanin.class);
		
		DomaciGradjanin gradjanin = new DomaciGradjanin();
		gradjanin.setGradjanin(model);
		
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(model.getKorisnickoIme(), gradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}

}
