package vakcinac.io.civil.servant.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.models.os.Tzaposleni;
import vakcinac.io.core.security.Authority;
import vakcinac.io.core.security.User;

@Service
public class ZaposleniService implements UserDetailsService {
	
	private SluzbenikService sluzbenikService;
	private ZdravstveniRadnikService zdravstveniRadnikService;
	
	@Autowired
	public ZaposleniService(SluzbenikService sluzbenikService, ZdravstveniRadnikService zdravstveniRadnikService) {
		this.sluzbenikService = sluzbenikService;
		this.zdravstveniRadnikService = zdravstveniRadnikService;
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Tzaposleni zaposleni = findByKorisnickoIme(username);
        List<GrantedAuthority> authorities = getGradjaninAuthorities(zaposleni);
        
        if (zaposleni == null) {
        	return null;
        }
        
        return new User(zaposleni.getKorisnickoIme(), zaposleni.getLozinka(), authorities);
    }
	
	public int totalNumberOfZdravstveniRadnici() throws XMLDBException, IOException {
		return zdravstveniRadnikService.totalNumber();
	}
	
	private List<GrantedAuthority> getGradjaninAuthorities(Tzaposleni zaposleni) {
		String role = zaposleni instanceof Sluzbenik ? "Sluzbenik" : "ZdravstveniRadnik";
		GrantedAuthority grantedAuthority = new Authority("ROLE_" + role);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(grantedAuthority);
        
		return authorities;
    }
	
	public Tzaposleni create(Tzaposleni zaposleni) throws IOException {
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
			throw new BadLogicException("Zaposleni sa unesenim korisničkim imenom već postoji.");
		}
		
		Tzaposleni existingZaposleniByJmbg = findById(zaposleni.getJmbg());
		if (existingZaposleniByJmbg != null) {
			throw new BadLogicException("Zaposleni sa unesenim jmbg već postoji.");
		}
		
		Tzaposleni existingZaposleniByEmail = findByEmail(zaposleni.getEmail());
		if (existingZaposleniByEmail != null) {
			throw new BadLogicException("Zaposleni sa unesenim email-om već postoji.");
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
	
	public Tzaposleni findByEmail(String email) {
		Tzaposleni sluzbenik = sluzbenikService.findByEmail(email);
		if (sluzbenik != null) {
			return sluzbenik;
		}
		
		Tzaposleni zdravstveniRadnik = zdravstveniRadnikService.findByEmail(email);
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
