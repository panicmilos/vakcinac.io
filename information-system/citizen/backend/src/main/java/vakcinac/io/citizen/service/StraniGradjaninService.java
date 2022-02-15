package vakcinac.io.citizen.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.repository.StraniGradjaninRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;

@Service
@RequestScope
public class StraniGradjaninService extends BaseService<StraniGradjanin> {
	
	public StraniGradjaninService(StraniGradjaninRepository straniGradjaninRepository, CitizenJenaRepository jenaRepository) {
		super(straniGradjaninRepository, jenaRepository);
	}
	
	public StraniGradjanin create(StraniGradjanin straniGradjanin) {
		String id = straniGradjanin.getIdentifikacioniDokument() == 0 ? straniGradjanin.getBrojPasosa() : straniGradjanin.getEbs();
		
		validate(id, straniGradjanin);
		
		return create(id, straniGradjanin);
	}
	
	public StraniGradjanin findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/strani-gradjani')//*:strani-gradjanin/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, StraniGradjanin.class);
	}
	
	private void validate(String id, StraniGradjanin straniGradjanin) {
		if (id == null || id.isEmpty()) {
			throw new BadLogicException("Identifikacioni broj nije validan.");
		}
		
		StraniGradjanin existingStraniGradjanin = read(id);
		if (existingStraniGradjanin != null) {
			throw new MissingEntityException("Građanin već postoji.");
		}
	}
}
