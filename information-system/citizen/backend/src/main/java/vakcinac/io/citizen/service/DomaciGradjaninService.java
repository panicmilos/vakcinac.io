package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.repository.DomaciGradjaninRepository;
import vakcinac.io.core.exceptions.MissingEntityException;

@Service
public class DomaciGradjaninService extends BaseService<DomaciGradjanin> {
	
	public DomaciGradjaninService(DomaciGradjaninRepository domaciGradjaninRepository) {
		super(domaciGradjaninRepository);
	}
	
	public DomaciGradjanin create(DomaciGradjanin domaciGradjanin) {
		String id = domaciGradjanin.getJmbg();
		
		DomaciGradjanin existingDomaciGradjanin = read(id);
		if (existingDomaciGradjanin != null) {
			throw new MissingEntityException("Domaći građanin već postoji.");
		}
		
		return create(id, domaciGradjanin);
	}
}
