package vakcinac.io.civil.servant.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.IzjavaInteresovanjaZaVakcinisanjeFactory;
import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.requests.CreateIzjavaRequest;
import vakcinac.io.civil.servant.service.IzjavaService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/izjave")
public class IzjavaController extends ControllerBase {

	@Autowired
	private IzjavaService izjavaService;
	
	@Autowired
	public IzjavaController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}
	
	@PostMapping
	public ResponseEntity<IzjavaInteresovanjaZaVakcinisanje> apply(@RequestBody CreateIzjavaRequest createIzjavaRequest) throws XMLDBException, IOException {
		
		IzjavaInteresovanjaZaVakcinisanje izjava = IzjavaInteresovanjaZaVakcinisanjeFactory.create(createIzjavaRequest);
		
		IzjavaInteresovanjaZaVakcinisanje createdIzjava = izjavaService.create(izjava);
		
		return ResponseEntity.ok(createdIzjava);
	}
	
}
