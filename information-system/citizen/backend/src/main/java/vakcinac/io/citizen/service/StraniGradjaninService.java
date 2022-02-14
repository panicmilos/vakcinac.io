package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.repository.StraniGradjaninRepository;

@Service
public class StraniGradjaninService extends BaseService<StraniGradjanin> {
	
	public StraniGradjaninService(StraniGradjaninRepository straniGradjaninRepository) {
		super(straniGradjaninRepository);
	}
	
	@Override
	public StraniGradjanin create(String id, StraniGradjanin straniGradjanin) {
		return super.create(id, straniGradjanin);
	}
}
