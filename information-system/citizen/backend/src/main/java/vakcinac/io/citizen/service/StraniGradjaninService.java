package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.repository.StraniGradjaninRepository;
import vakcinac.io.core.exceptions.MissingEntityException;

@Service
public class StraniGradjaninService extends BaseService<StraniGradjanin> {
	
	public StraniGradjaninService(StraniGradjaninRepository straniGradjaninRepository) {
		super(straniGradjaninRepository);
	}
	
	public StraniGradjanin create(StraniGradjanin straniGradjanin) {
		String id = straniGradjanin.getIdentifikacioniDokument() == 0 ? straniGradjanin.getBrojPasosa() : straniGradjanin.getEbs();
		
		StraniGradjanin existingStraniGradjanin = read(id);
		if (existingStraniGradjanin != null) {
			throw new MissingEntityException("Građanin već postoji.");
		}
		
		return create(id, straniGradjanin);
	}
}
