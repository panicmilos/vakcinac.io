package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.requests.CreateVakcinaRequest;
import vakcinac.io.civil.servant.service.VakcinaService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/vakcine")
public class VakcineController extends ControllerBase {
	
	@Autowired
	private VakcinaService vakcinaService;
	
	@Autowired
	public VakcineController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}

	
	@PostMapping
	public ResponseEntity<Vakcina> createVakcina(@RequestBody CreateVakcinaRequest vakcinaRequest) {
//		validate(vakcinaRequest);

		Vakcina vakcina = (Vakcina) map(vakcinaRequest, Vakcina.class);
		
		Vakcina createdVakcina = vakcinaService.create(vakcina);
		
		return ResponseEntity.ok(createdVakcina);
	}
	
//	@PutMapping
//	public ResponseEntity<>
	
}
