package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.response.AuthenticatedGradjaninResponse;
import vakcinac.io.citizen.security.utils.JwtUtil;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tgradjanin;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    private GradjaninService gradjaninService;

    @Autowired
    public AuthenticationService(
        AuthenticationManager authenticationManager,
        JwtUtil jwtUtil,
        GradjaninService gradjaninService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.gradjaninService = gradjaninService;
    }

    public AuthenticatedGradjaninResponse authenticate(String korisnickoIme, String lozinka) {
        Tgradjanin gradjanin = gradjaninService.findByKorisnickoIme(korisnickoIme);
        if (gradjanin == null) {
        	throw new MissingEntityException("Građanin sa zadatim korisničkim imenom ne postoji.");
        }
        
        String id;
        if (gradjanin instanceof DomaciGradjanin) {
        	id = ((DomaciGradjanin) gradjanin).getJmbg();
        }
        else {
        	StraniGradjanin straniGradjanin = (StraniGradjanin) gradjanin;
        	id = straniGradjanin.getIdentifikacioniDokument() == 0 ? straniGradjanin.getBrojPasosa() : straniGradjanin.getEbs();
        }

        String jwt = generateJwt(korisnickoIme, lozinka, id);

        return new AuthenticatedGradjaninResponse(korisnickoIme, jwt);
    }

    private String generateJwt(String korisnickoIme, String lozinka, String gradjaninId) {
    	Authentication authentication = getAuthentication(korisnickoIme, lozinka);

        return jwtUtil.generateToken(authentication, korisnickoIme, gradjaninId);
    }

    private Authentication getAuthentication(String korisnickoIme, String lozinka) {
    	UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(korisnickoIme, lozinka);
    	Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
