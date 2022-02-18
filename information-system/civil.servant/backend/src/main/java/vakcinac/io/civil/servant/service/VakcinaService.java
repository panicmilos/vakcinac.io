package vakcinac.io.civil.servant.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.repository.VakcinaRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.civil.servant.results.vak.VakcineResult;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;

@Service
public class VakcinaService extends BaseService<Vakcina> {
	
	private VakcinaRepository vakcinaRepository;
	
	public VakcinaService(VakcinaRepository vakcinaRepository, CivilServantJenaRepository jenaRepository) {
		super(vakcinaRepository, jenaRepository);
		
		this.vakcinaRepository = vakcinaRepository;
	}
	
	public VakcineResult findAll() throws XMLDBException, IOException {
		return vakcinaRepository.findAll();
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
