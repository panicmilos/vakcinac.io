package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vakcinac.io.civil.servant.response.AuthenticatedZaposleniResponse;
import vakcinac.io.civil.servant.security.utils.JwtUtil;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tzaposleni;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    private ZaposleniService zaposleniService;

    @Autowired
    public AuthenticationService(
        AuthenticationManager authenticationManager,
        JwtUtil jwtUtil,
        ZaposleniService zaposleniService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.zaposleniService = zaposleniService;
    }

    public AuthenticatedZaposleniResponse authenticate(String korisnickoIme, String lozinka) {
        Tzaposleni zaposleni = zaposleniService.findByKorisnickoIme(korisnickoIme);
        if (zaposleni == null) {
        	throw new MissingEntityException("Zaposleni sa zadatim korisničkim imenom ne postoji.");
        }
        
        String id = zaposleni.getJmbg();

        String jwt = generateJwt(korisnickoIme, lozinka, id);

        return new AuthenticatedZaposleniResponse(korisnickoIme, jwt);
    }
    
    public String getCurrentWorkerId() {
    	String korisnickoIme = getCurrentUserUsername();
    	
    	Tzaposleni zaposleni = zaposleniService.findByKorisnickoIme(korisnickoIme);
    	return zaposleni.getJmbg();
    }
    
    public String getCurrentUserUsername() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	return authentication.getName();
    }

    private String generateJwt(String korisnickoIme, String lozinka, String zaposleniId) {
    	Authentication authentication = getAuthentication(korisnickoIme, lozinka);

        return jwtUtil.generateToken(authentication, korisnickoIme, zaposleniId);
    }

    private Authentication getAuthentication(String korisnickoIme, String lozinka) {
    	UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(korisnickoIme, lozinka);
    	Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
