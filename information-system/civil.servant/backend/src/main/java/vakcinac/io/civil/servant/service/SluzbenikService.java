package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.repository.SluzbenikRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;

@Service
@RequestScope
public class SluzbenikService extends BaseService<Sluzbenik> {
	
	public SluzbenikService(SluzbenikRepository sluzbenikRepository, CivilServantJenaRepository jenaRepository) {
		super(sluzbenikRepository, jenaRepository);
	}
	
	public Sluzbenik create(Sluzbenik sluzbenik) {
		String id = sluzbenik.getJmbg();
		
		validate(id, sluzbenik);
		
		return create(id, sluzbenik);
	}
	
	public Sluzbenik findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/sluzbenici')//*:sluzbenik/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, Sluzbenik.class);
	}
	
	private void validate(String id, Sluzbenik sluzbenik) {
		Sluzbenik existingSluzbenik = read(id);
		if (existingSluzbenik != null) {
			throw new MissingEntityException("Službenik već postoji.");
		}
	}
}
