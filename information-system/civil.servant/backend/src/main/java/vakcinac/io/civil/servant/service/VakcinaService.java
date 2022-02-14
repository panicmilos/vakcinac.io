package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.repository.VakcinaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;

@Service
@RequestScope
public class VakcinaService extends BaseService<Vakcina> {
	
	public VakcinaService(VakcinaRepository vakcinaRepository, CivilServantJenaRepository jenaRepository) {
		super(vakcinaRepository, jenaRepository);
	}
	
	@Override
	public Vakcina create(Vakcina vakcina) {
		String id = vakcina.getSerija();
		
		Vakcina existingVakcina = read(id);
		if (existingVakcina != null) {
			throw new MissingEntityException("Vakcina već postoji.");
		}
		
		return create(id, vakcina);
	}
	
}
