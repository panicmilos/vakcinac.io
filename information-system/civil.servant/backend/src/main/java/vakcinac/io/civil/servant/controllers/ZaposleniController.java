package vakcinac.io.civil.servant.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.requests.CreateSluzbenikRequest;
import vakcinac.io.civil.servant.requests.CreateZdravstveniRadnikRequest;
import vakcinac.io.civil.servant.service.AuthenticationService;
import vakcinac.io.civil.servant.service.ZaposleniService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tzaposleni;

@Controller
@CrossOrigin("*")
@RequestMapping("/zaposleni")
public class ZaposleniController extends ControllerBase {
	
	private ZaposleniService zaposleniService;
	private AuthenticationService authService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public ZaposleniController(ModelMapper mapper, CivilServantValidator validator, ZaposleniService zaposleniService, AuthenticationService authService, PasswordEncoder passwordEncoder) {
		super(mapper, validator);
		this.zaposleniService = zaposleniService;
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tzaposleni> getZaposleni(@PathVariable("id") String id) {
		Tzaposleni zaposleni = zaposleniService.findById(id);
		if (zaposleni == null) {
			throw new MissingEntityException(String.format("Zaposleni sa željenim id (%s) ne postoji.", id));
		}
		
		return ResponseEntity.ok(zaposleni);
	}
	
	@PreAuthorize("hasAnyRole('Sluzbenik', 'ZdravstveniRadnik')")
	@GetMapping("korisnicko-ime/{korisnickoIme}")
	public ResponseEntity<UserDetails> getZaposleniByKorisnickoIme(@PathVariable("korisnickoIme") String korisnickoIme) {
		UserDetails zaposleni = zaposleniService.loadUserByUsername(korisnickoIme);
		if (zaposleni == null) {
			throw new MissingEntityException(String.format("Zaposleni sa željenim korisničkim imenom (%s) ne postoji.", korisnickoIme));
		}
		
		String currentUserUsername = authService.getCurrentUserUsername();
		if (!currentUserUsername.equals(zaposleni.getUsername())) {
			throw new BadLogicException("Možete pristupiti samo svojim podacima.");
		}
		
		return ResponseEntity.ok(zaposleni);
	}
	
	@PreAuthorize("hasAnyRole('Sluzbenik')")
	@PostMapping("/sluzbenik")
	public ResponseEntity<Sluzbenik> createSluzbenik(@RequestBody CreateSluzbenikRequest createSluzbenikRequest) throws IOException {
		validate(createSluzbenikRequest);
		System.out.println(createSluzbenikRequest);
		
		Sluzbenik sluzbenik = (Sluzbenik) map(createSluzbenikRequest, Sluzbenik.class);
		sluzbenik.setLozinka(passwordEncoder.encode(sluzbenik.getLozinka()));
		
		Sluzbenik createdSluzbenik = (Sluzbenik) zaposleniService.create(sluzbenik);
		
		return ResponseEntity.ok(createdSluzbenik);
	}
	
	@PreAuthorize("hasAnyRole('Sluzbenik')")
	@PostMapping("/zdravstveni-radnik")
	public ResponseEntity<ZdravstveniRadnik> createZdravstveniRadnik(@RequestBody CreateZdravstveniRadnikRequest createZdravstveniRadnikRequest) throws IOException {
		validate(createZdravstveniRadnikRequest);
		
		ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) map(createZdravstveniRadnikRequest, ZdravstveniRadnik.class);
		zdravstveniRadnik.setLozinka(passwordEncoder.encode(zdravstveniRadnik.getLozinka()));
		
		ZdravstveniRadnik createdZdravstveniRadnik = (ZdravstveniRadnik) zaposleniService.create(zdravstveniRadnik);
		
		return ResponseEntity.ok(createdZdravstveniRadnik);
	}
	
	@PreAuthorize("hasAnyRole('Sluzbenik')")
	@GetMapping("/total/zdravstveni-radnici")
	public ResponseEntity<Integer> getTotalNumberOfZdravstveniRadnici() throws IOException, XMLDBException {
		int totalNumber = zaposleniService.totalNumberOfZdravstveniRadnici();
		
		return ResponseEntity.ok(totalNumber);
	}
}
