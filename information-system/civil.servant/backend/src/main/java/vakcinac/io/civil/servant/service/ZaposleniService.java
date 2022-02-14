package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.models.os.Tzaposleni;

@Service
public class ZaposleniService {
	
	private SluzbenikService sluzbenikService;
	private ZdravstveniRadnikService zdravstveniRadnikService;
	
	@Autowired
	public ZaposleniService(SluzbenikService sluzbenikService, ZdravstveniRadnikService zdravstveniRadnikService) {
		this.sluzbenikService = sluzbenikService;
		this.zdravstveniRadnikService = zdravstveniRadnikService;
	}
	
	public Tzaposleni create(Tzaposleni zaposleni) {
		validate(zaposleni);
		
		if (zaposleni instanceof Sluzbenik) {
			return sluzbenikService.create((Sluzbenik) zaposleni);
		}
		else {
			return zdravstveniRadnikService.create((ZdravstveniRadnik) zaposleni);
		}
	}
	
	private void validate(Tzaposleni zaposleni) {
		Tzaposleni existingZaposleniByKorisnickoIme = findByKorisnickoIme(zaposleni.getKorisnickoIme());
		if (existingZaposleniByKorisnickoIme != null) {
			throw new BadLogicException("Zaposleni sa korisničkim imenom već postoji.");
		}
	}
	
	public Tzaposleni findByKorisnickoIme(String korisnickoIme) {
		Tzaposleni sluzbenik = sluzbenikService.findByKorisnickoIme(korisnickoIme);
		if (sluzbenik != null) {
			return sluzbenik;
		}
		
		Tzaposleni zdravstveniRadnik = zdravstveniRadnikService.findByKorisnickoIme(korisnickoIme);
		if (zdravstveniRadnik != null) {
			return zdravstveniRadnik;
		}
		
		return null;
	}
	
	public Tzaposleni findById(String id) {
		Tzaposleni sluzbenik = sluzbenikService.read(id);
		if (sluzbenik != null) {
			return sluzbenik;
		}
		
		Tzaposleni zdravstveniRadnik = zdravstveniRadnikService.read(id);
		if (zdravstveniRadnik != null) {
			return zdravstveniRadnik;
		}
		
		return null;
	}

}
