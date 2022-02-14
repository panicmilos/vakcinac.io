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

@Controller
@RequestMapping("/gradjani")
public class DomaciGradjaniController {
	
	private ModelMapper mapper;
	private CitizenValidator validator;
	private DomaciGradjaninService domaciGradjaninService;
	
	@Autowired
	public DomaciGradjaniController(ModelMapper mapper, CitizenValidator validator, DomaciGradjaninService domaciGradjaninService) {
		this.mapper = mapper;
		this.validator = validator;
		this.domaciGradjaninService = domaciGradjaninService;
	}
	
	@PostMapping
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody CreateDomaciGradjanin domaciGradjaninRequest) {
		ValidationResult result = validator.validate(domaciGradjaninRequest);
		if (!result.isValid()) {
			System.out.print("GRESSKAKAK");
		}
		
		DomaciGradjanin domaciGradjanin = new DomaciGradjanin();
		
		TdomaciGradjanin model = mapper.map(domaciGradjaninRequest, TdomaciGradjanin.class);
		domaciGradjanin.setGradjanin(model);
		
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(model.getKorisnickoIme(), domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}

}
