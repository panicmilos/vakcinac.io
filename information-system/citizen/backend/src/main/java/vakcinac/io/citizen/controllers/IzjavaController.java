package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.citizen.service.IzjavaService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateIzjavaRequest;

@Controller
@RequestMapping("izjave")
public class IzjavaController extends ControllerBase {

	@Autowired
	private IzjavaService izjavaService;
	
	@Autowired
	public IzjavaController(ModelMapper mapper, CitizenValidator validator) {
		super(mapper, validator);
	}
	
	@GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "/" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(izjavaService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(izjavaService.readPreview(id, type));
    }

	@PostMapping
	public ResponseEntity<IzjavaInteresovanjaZaVakcinisanje> apply(@RequestBody CreateIzjavaRequest createIzjavaRequest) throws Exception {
		validate(createIzjavaRequest);
		
		IzjavaInteresovanjaZaVakcinisanje izjava = mapper.map(createIzjavaRequest, IzjavaInteresovanjaZaVakcinisanje.class);
		
		IzjavaInteresovanjaZaVakcinisanje createdIzjava = izjavaService.create(izjava);
		
		return ResponseEntity.ok(createdIzjava);
	}

	
}
