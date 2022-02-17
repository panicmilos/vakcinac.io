package vakcinac.io.citizen.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.security.JwtStore;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.results.doc.CitizenDocumentsResult;
import vakcinac.io.core.security.Authority;
import vakcinac.io.core.security.User;
import vakcinac.io.core.utils.HttpUtils;

@Service
@RequestScope
public class GradjaninService implements UserDetailsService {
	
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;

	private DomaciGradjaninService domaciGradjaninService;
	private StraniGradjaninService straniGradjaninService;
	private JenaRepository jenaRepository;
	private JwtStore jwtStore;
	private RestTemplate restTemplate;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public GradjaninService(
			DomaciGradjaninService domaciGradjaninService,
			StraniGradjaninService straniGradjaninService,
			JenaRepository jenaRepository,
			JwtStore jwtStore,
			RestTemplate restTemplate,
			PasswordEncoder passwordEncoder) {
		this.domaciGradjaninService = domaciGradjaninService;
		this.straniGradjaninService = straniGradjaninService;
		this.jenaRepository = jenaRepository;
		this.jwtStore = jwtStore;
		this.restTemplate = restTemplate;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Tgradjanin gradjanin = findByKorisnickoIme(username);
        List<GrantedAuthority> accountAuthorities = getGradjaninAuthorities(gradjanin);
        
        if (gradjanin == null) {
        	return null;
        }

        return new User(gradjanin.getKorisnickoIme(), gradjanin.getLozinka(), accountAuthorities);
    }
	
	private List<GrantedAuthority> getGradjaninAuthorities(Tgradjanin gradjanin) {
		String role = gradjanin instanceof DomaciGradjanin ? "DomaciGradjanin" : "StraniGradjanin";
		GrantedAuthority grantedAuthority = new Authority("ROLE_" + role);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(grantedAuthority);
        
		return authorities;
    }
	
	public CitizenDocumentsResult getDocumentsFor(String jmbg) throws IOException {
		return jenaRepository.findDocumentsFor(jmbg);
	}
	
	public CitizenDocumentsResult getAllDocumentsFor(String jmbg) throws IOException {
		CitizenDocumentsResult remoteDocuments = getAllRemoteDocumentsFor(jmbg);
		CitizenDocumentsResult localDocuments = getDocumentsFor(jmbg);
		
		localDocuments.getCitizenDocument().addAll(remoteDocuments.getCitizenDocument());
		
		return localDocuments;
	}
	
	private CitizenDocumentsResult getAllRemoteDocumentsFor(String jmbg) throws IOException {
		HttpEntity<?> httpEntity = HttpUtils.configureHeader(jwtStore.getJwt());
		ResponseEntity<CitizenDocumentsResult> response = restTemplate.exchange(String.format("%s/gradjani/%s/documents", sluzbenikUrl, jmbg), HttpMethod.GET, httpEntity, CitizenDocumentsResult.class);
		
		return response.getBody();
	}
	
	public Tgradjanin create(Tgradjanin gradjanin) throws IOException {		
		gradjanin.setLozinka(passwordEncoder.encode(gradjanin.getLozinka()));
		
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
