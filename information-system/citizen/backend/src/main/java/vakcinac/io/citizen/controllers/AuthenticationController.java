package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.requests.AuthenticateRequest;
import vakcinac.io.citizen.response.AuthenticatedGradjaninResponse;
import vakcinac.io.citizen.service.AuthenticationService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
	
	@Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
	
	@PostMapping("/authenticate")
    public ResponseEntity<AuthenticatedGradjaninResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        AuthenticatedGradjaninResponse authenticatedGradjanin = authenticationService.authenticate(authenticateRequest.getKorisnickoIme(),
                authenticateRequest.getLozinka());

        return ResponseEntity.ok(authenticatedGradjanin);
    }
}
