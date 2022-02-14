package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;

import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.repository.ZdravstveniRadnikRepository;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.services.BaseService;

@Service
public class ZdravstveniRadnikService extends BaseService<ZdravstveniRadnik> {
	
	public ZdravstveniRadnikService(ZdravstveniRadnikRepository zdravstveniRadnikRepository) {
		super(zdravstveniRadnikRepository);
	}
	
	public ZdravstveniRadnik create(ZdravstveniRadnik zdravstveniRadnik) {
		String id = zdravstveniRadnik.getJmbg();
		
		validate(id, zdravstveniRadnik);
		
		return create(id, zdravstveniRadnik);
	}
	
	public ZdravstveniRadnik findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/zdravstveni-radnici')//*:zdravstveni-radnik/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, ZdravstveniRadnik.class);
	}
	
	private void validate(String id, ZdravstveniRadnik zdravstveniRadnik) {
		ZdravstveniRadnik existingZdravstveniRadnik = read(id);
		if (existingZdravstveniRadnik != null) {
			throw new MissingEntityException("Zdravstveni radnik već postoji.");
		}
	}
}
