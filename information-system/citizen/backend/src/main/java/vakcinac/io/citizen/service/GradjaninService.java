package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.models.os.Tgradjanin;

@Service
public class GradjaninService {
	
	private DomaciGradjaninService domaciGradjaninService;
	private StraniGradjaninService straniGradjaninService;
	
	@Autowired
	public GradjaninService(DomaciGradjaninService domaciGradjaninService, StraniGradjaninService straniGradjaninService) {
		this.domaciGradjaninService = domaciGradjaninService;
		this.straniGradjaninService = straniGradjaninService;
	}
	
	public Tgradjanin create(Tgradjanin gradjanin) {
		validate(gradjanin);
		
		if (gradjanin instanceof DomaciGradjanin) {
			return domaciGradjaninService.create((DomaciGradjanin) gradjanin);
		}
		else {
			return straniGradjaninService.create((StraniGradjanin) gradjanin);
		}
	}
	
	private void validate(Tgradjanin gradjanin) {
		Tgradjanin existingGradjaninByKorisnickoIme = findByKorisnickoIme(gradjanin.getKorisnickoIme());
		if (existingGradjaninByKorisnickoIme != null) {
			throw new BadLogicException("Građanin sa korisničkim imenom već postoji.");
		}
	}
	 
	public Tgradjanin findByKorisnickoIme(String korisnickoIme) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.findByKorisnickoIme(korisnickoIme);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.findByKorisnickoIme(korisnickoIme);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		return null;
	}

	public Tgradjanin findById(String id) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.read(id);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.read(id);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		return null;
	}
}
