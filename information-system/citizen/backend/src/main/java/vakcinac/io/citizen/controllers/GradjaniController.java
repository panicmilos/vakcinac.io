package vakcinac.io.citizen.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.requests.CreateDomaciGradjaninRequest;
import vakcinac.io.citizen.requests.CreateStraniGradjaninRequest;
import vakcinac.io.citizen.service.AuthenticationService;
import vakcinac.io.citizen.service.GradjaninService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.results.doc.CitizenDocumentsResult;

@Controller
@CrossOrigin("*")
@RequestMapping("/gradjani")
public class GradjaniController extends ControllerBase {
	
	private GradjaninService gradjaninService;
	private AuthenticationService authService;
	
	@Autowired
	public GradjaniController(ModelMapper mapper, CitizenValidator validator, GradjaninService gradjaninService, AuthenticationService authService) {
		super(mapper, validator);
		this.gradjaninService = gradjaninService;
		this.authService = authService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tgradjanin> getGradjanin(@PathVariable("id") String id) {
		
		Tgradjanin gradjanin = gradjaninService.findById(id);
		if (gradjanin == null) {
			throw new MissingEntityException(String.format("Građanin sa željenim id (%s) ne postoji.", id));
		}
		
		return ResponseEntity.ok(gradjanin);
	}
	
	@GetMapping("/{id}/documents")
	public ResponseEntity<CitizenDocumentsResult> getDocumentsForGradjanin(@PathVariable("id") String id) throws IOException {
				
		return ResponseEntity.ok(gradjaninService.getDocumentsFor(id));
	}
	
	@GetMapping("/{id}/documents/all")
	public ResponseEntity<CitizenDocumentsResult> getAllDocumentsForGradjanin(@PathVariable("id") String id) throws IOException {
				
		return ResponseEntity.ok(gradjaninService.getAllDocumentsFor(id));
	}

	@PostMapping("/domaci")
	public ResponseEntity<DomaciGradjanin> createDomaciGradjanin(@RequestBody CreateDomaciGradjaninRequest createDomaciGradjaninRequest) throws IOException {
		validate(createDomaciGradjaninRequest);
		
		DomaciGradjanin domaciGradjanin = (DomaciGradjanin) map(createDomaciGradjaninRequest, DomaciGradjanin.class);
		
		DomaciGradjanin createdDomaciGradjanin = (DomaciGradjanin) gradjaninService.create(domaciGradjanin);
		
		return ResponseEntity.ok(createdDomaciGradjanin);
	}
	
	@PostMapping("/strani")
	public ResponseEntity<StraniGradjanin> createStraniGradjanin(@RequestBody CreateStraniGradjaninRequest createStraniGradjaninRequest) throws IOException {
		validate(createStraniGradjaninRequest);
		
		StraniGradjanin straniGradjanin = (StraniGradjanin) map(createStraniGradjaninRequest, StraniGradjanin.class);
		
		StraniGradjanin createdStraniGradjanin = (StraniGradjanin) gradjaninService.create(straniGradjanin);
		
		return ResponseEntity.ok(createdStraniGradjanin);
	}
	
	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin')")
	@GetMapping("korisnicko-ime/{korisnickoIme}")
	public ResponseEntity<UserDetails> getGradjaninByKorisnickoIme(@PathVariable("korisnickoIme") String korisnickoIme) {
		UserDetails gradjanin = gradjaninService.loadUserByUsername(korisnickoIme);
		if (gradjanin == null) {
			throw new MissingEntityException(String.format("Građanin sa željenim korisničkim imenom (%s) ne postoji.", korisnickoIme));
		}

		String currentUserUsername = authService.getCurrentUserUsername();
		if (!currentUserUsername.equals(gradjanin.getUsername())) {
			throw new BadLogicException("Možete pristupiti samo svojim podacima.");
		}

		return ResponseEntity.ok(gradjanin);
	}
}
