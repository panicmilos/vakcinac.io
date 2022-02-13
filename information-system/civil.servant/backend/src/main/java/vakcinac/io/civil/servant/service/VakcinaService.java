package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.repository.VakcinaRepository;

@Service
public class VakcinaService extends BaseService<Vakcina> {
	
	public VakcinaService(VakcinaRepository vakcinaRepository) {
		super(vakcinaRepository);
	}
	
	@Override
	public Vakcina create(String id, Vakcina vakcina) {
		Vakcina existingVakcina = read(id);
		
		if (existingVakcina != null) {
			System.out.println("Postojim brate");
		}
		
		return super.create(id, vakcina);
	}
	
}
