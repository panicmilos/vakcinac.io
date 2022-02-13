package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.service.DomaciGradjaninService;

@Controller
@RequestMapping("/gradjani")
public class DomaciGradjaniController {
	
	@Autowired
	private DomaciGradjaninService domaciGradjaninService;
	
	@PostMapping
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody DomaciGradjanin domaciGradjanin) {
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(domaciGradjanin.getGradjanin().getKorisnickoIme(), domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}

}
