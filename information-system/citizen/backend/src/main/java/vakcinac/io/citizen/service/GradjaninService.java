package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vakcinac.io.core.exceptions.MissingEntityException;
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

	public Tgradjanin findById(String id) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.read(id);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.read(id);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		throw new MissingEntityException("Gradjanin ne postoji.");
	}
}
