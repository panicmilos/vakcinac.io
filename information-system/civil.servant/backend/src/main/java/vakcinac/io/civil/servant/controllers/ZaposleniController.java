package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.requests.CreateSluzbenikRequest;
import vakcinac.io.civil.servant.requests.CreateZdravstveniRadnikRequest;
import vakcinac.io.civil.servant.service.ZaposleniService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.models.os.Tzaposleni;

@Controller
@RequestMapping("/zaposleni")
public class ZaposleniController extends ControllerBase {
	
	private ZaposleniService zaposleniService;
	
	@Autowired
	public ZaposleniController(ModelMapper mapper, CivilServantValidator validator, ZaposleniService zaposleniService) {
		super(mapper, validator);
		this.zaposleniService = zaposleniService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tzaposleni> getGradjanin(@PathVariable("id") String id) {
		Tzaposleni zaposleni = zaposleniService.findById(id);
		if (zaposleni == null) {
			throw new MissingEntityException(String.format("Zaposleni sa željenim id (%s) ne postoji.", id));
		}
		
		return ResponseEntity.ok(zaposleni);
	}
	
	@PostMapping("/sluzbenik")
	public ResponseEntity<Sluzbenik> createSluzbenik(@RequestBody CreateSluzbenikRequest createSluzbenikRequest) {
		validate(createSluzbenikRequest);
		
		Sluzbenik sluzbenik = (Sluzbenik) map(createSluzbenikRequest, Sluzbenik.class);
		
		Sluzbenik createdSluzbenik = (Sluzbenik) zaposleniService.create(sluzbenik);
		
		return ResponseEntity.ok(createdSluzbenik);
	}
	
	@PostMapping("/zdravstveni-radnik")
	public ResponseEntity<ZdravstveniRadnik> createStraniGradjanin(@RequestBody CreateZdravstveniRadnikRequest createZdravstveniRadnikRequest) {
		validate(createZdravstveniRadnikRequest);
		
		ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) map(createZdravstveniRadnikRequest, ZdravstveniRadnik.class);
		
		ZdravstveniRadnik createdZdravstveniRadnik = (ZdravstveniRadnik) zaposleniService.create(zdravstveniRadnik);
		
		return ResponseEntity.ok(createdZdravstveniRadnik);
	}
}
