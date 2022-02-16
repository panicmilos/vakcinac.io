package vakcinac.io.citizen.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.models.os.Tgradjanin;

@Service
@RequestScope
public class GradjaninService implements UserDetailsService {
	
	private DomaciGradjaninService domaciGradjaninService;
	private StraniGradjaninService straniGradjaninService;
	
	@Autowired
	public GradjaninService(DomaciGradjaninService domaciGradjaninService, StraniGradjaninService straniGradjaninService) {
		this.domaciGradjaninService = domaciGradjaninService;
		this.straniGradjaninService = straniGradjaninService;
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Tgradjanin gradjanin = findByKorisnickoIme(username);
        List<GrantedAuthority> accountAuthorities = getGradjaninAuthorities(gradjanin);

        return new User(gradjanin.getKorisnickoIme(), gradjanin.getLozinka(), accountAuthorities);
    }
	
	private List<GrantedAuthority> getGradjaninAuthorities(Tgradjanin gradjanin) {
		String role = gradjanin instanceof DomaciGradjanin ? "Domaci" : "Strani";
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(grantedAuthority);
        
		return authorities;
    }
	
	public Tgradjanin create(Tgradjanin gradjanin) throws IOException {		
		if (gradjanin instanceof DomaciGradjanin) {
			DomaciGradjanin domaciGradjanin = (DomaciGradjanin) gradjanin;
			validate(domaciGradjanin.getJmbg(), domaciGradjanin);
			
			return domaciGradjaninService.create((DomaciGradjanin) gradjanin);
		}
		else {
			StraniGradjanin straniGradjanin = (StraniGradjanin) gradjanin;
			String id = straniGradjanin.getIdentifikacioniDokument() == 0 ? straniGradjanin.getBrojPasosa() : straniGradjanin.getEbs();
			if (id == null || id.isEmpty()) {
				throw new BadLogicException("Identifikacioni broj nije validan.");
			}
			validate(id, straniGradjanin);
			
			return straniGradjaninService.create((StraniGradjanin) gradjanin);
		}
	}
	
	private void validate(String id, Tgradjanin gradjanin) {
		Tgradjanin existingGradjaninByKorisnickoIme = findByKorisnickoIme(gradjanin.getKorisnickoIme());
		if (existingGradjaninByKorisnickoIme != null) {
			throw new BadLogicException("Građanin sa unesenim korisničkim imenom već postoji.");
		}
		
		Tgradjanin existingGradjaninById = findById(id);
		if (existingGradjaninById != null) {
			throw new BadLogicException("Građanin sa unesenim id-em već postoji.");
		}
		
		Tgradjanin existingGradjaninByEmail = findByEmail(gradjanin.getEmail());
		if (existingGradjaninByEmail != null) {
			throw new BadLogicException("Građanin sa unesenim email-om već postoji.");
		}
	}
	 
	public Tgradjanin findByKorisnickoIme(String korisnickoIme) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.findByKorisnickoIme(korisnickoIme);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.findByKorisnickoIme(korisnickoIme);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		return null;
	}
	
	public Tgradjanin findByEmail(String email) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.findByEmail(email);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.findByEmail(email);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		return null;
	}

	public Tgradjanin findById(String id) {
		Tgradjanin domaciGradjanin = domaciGradjaninService.read(id);
		if (domaciGradjanin != null) {
			return domaciGradjanin;
		}
		
		Tgradjanin straniGradjanin = straniGradjaninService.read(id);
		if (straniGradjanin != null) {
			return straniGradjanin;
		}
		
		return null;
	}
}
