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
		
		validate(id, domaciGradjanin);
		
		return create(id, domaciGradjanin);
	}
	
	public DomaciGradjanin findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/domaci-gradjani')//*:domaci-gradjanin/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, DomaciGradjanin.class);
	}
	
	private void validate(String id, DomaciGradjanin domaciGradjanin) {
		DomaciGradjanin existingDomaciGradjanin = read(id);
		if (existingDomaciGradjanin != null) {
			throw new MissingEntityException("Građanin već postoji.");
		}
	}
}
