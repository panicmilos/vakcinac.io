package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.requests.AuthenticateRequest;
import vakcinac.io.civil.servant.response.AuthenticatedZaposleniResponse;
import vakcinac.io.civil.servant.service.AuthenticationService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
	
	@Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
	
	@PostMapping
    public ResponseEntity<AuthenticatedZaposleniResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        AuthenticatedZaposleniResponse authenticatedZaposleni = authenticationService.authenticate(authenticateRequest.getKorisnickoIme(),
                authenticateRequest.getLozinka());

        return ResponseEntity.ok(authenticatedZaposleni);
    }
}
