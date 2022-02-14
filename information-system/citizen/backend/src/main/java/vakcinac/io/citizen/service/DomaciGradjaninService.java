package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.repository.DomaciGradjaninRepository;

@Service
public class DomaciGradjaninService extends BaseService<DomaciGradjanin> {
	
	public DomaciGradjaninService(DomaciGradjaninRepository domaciGradjaninRepository) {
		super(domaciGradjaninRepository);
	}
	
	@Override
	public DomaciGradjanin create(String id, DomaciGradjanin domaciGradjanin) {
//		DomaciGradjanin existingDomaciGradjanin = read(id);
//		
//		if (existingDomaciGradjanin != null) {
//			System.out.println("Postojim brate");
//		}
		
		return super.create(id, domaciGradjanin);
	}
}
