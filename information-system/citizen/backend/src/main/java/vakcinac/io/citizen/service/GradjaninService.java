package vakcinac.io.citizen.service;

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
	
	public Tgradjanin create(Tgradjanin gradjanin) {
		validate(gradjanin);
		
		if (gradjanin instanceof DomaciGradjanin) {
			return domaciGradjaninService.create((DomaciGradjanin) gradjanin);
		}
		else {
			return straniGradjaninService.create((StraniGradjanin) gradjanin);
		}
	}
	
	private void validate(Tgradjanin gradjanin) {
		Tgradjanin existingGradjaninByKorisnickoIme = findByKorisnickoIme(gradjanin.getKorisnickoIme());
		if (existingGradjaninByKorisnickoIme != null) {
			throw new BadLogicException("Građanin sa korisničkim imenom već postoji.");
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
