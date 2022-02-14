package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.dgradj.TdomaciGradjanin;
import vakcinac.io.citizen.request.DomaciGradjaninRequest;
import vakcinac.io.citizen.service.DomaciGradjaninService;

@Controller
@RequestMapping("/gradjani")
public class DomaciGradjaniController {
	
	@Autowired
	private DomaciGradjaninService domaciGradjaninService;
	
	@PostMapping
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody DomaciGradjaninRequest domaciGradjaninRequest) {
		DomaciGradjanin domaciGradjanin = new DomaciGradjanin();
		TdomaciGradjanin model = new TdomaciGradjanin();
		domaciGradjanin.setGradjanin(model);
		
		model.setIme(domaciGradjaninRequest.getIme());
		model.setPrezime(domaciGradjaninRequest.getPrezime());
		model.setKorisnickoIme(domaciGradjaninRequest.getKorisnickoIme());
		model.setLozinka(domaciGradjaninRequest.getLozinka());
		model.setDatumRodjenja(domaciGradjaninRequest.getDatumRodjenja());
		model.setPol(domaciGradjaninRequest.getPol());
		model.setEmail(domaciGradjaninRequest.getEmail());
		model.setImeRoditelja(domaciGradjaninRequest.getImeRoditelja());
		model.setMestoRodjenja(domaciGradjaninRequest.getMestoRodjenja());
		model.setAdresa(domaciGradjaninRequest.getAdresa());
		model.setMesto(domaciGradjaninRequest.getMesto());
		model.setOpstina(domaciGradjaninRequest.getOpstina());
		model.setJmbg(domaciGradjaninRequest.getJmbg());
		model.setBrojMobilnogTelefona(domaciGradjaninRequest.getBrojMobilnogTelefona());
		model.setBrojFiksnogTelefona(domaciGradjaninRequest.getBrojFiksnogTelefona());
		
		DomaciGradjanin createdDomaciGradjanin = domaciGradjaninService.create(model.getKorisnickoIme(), domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}

}
