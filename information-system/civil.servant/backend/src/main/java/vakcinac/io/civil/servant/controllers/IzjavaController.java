package vakcinac.io.civil.servant.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.factories.IzjavaInteresovanjaZaVakcinisanjeFactory;
import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.service.IzjavaService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateIzjavaRequest;

@Controller
@RequestMapping(path = "/izjave", produces = { "application/xml" })
public class IzjavaController extends ControllerBase {

	@Autowired
	private IzjavaService izjavaService;
	
	@Autowired
	public IzjavaController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}
	
	@GetMapping("/{id1}/{id2}/preview")
    public ResponseEntity<?> preview(@PathVariable String id1, @PathVariable String id2, @RequestParam(required = false) String type) throws Exception {
    	String id = id1 + "_" + id2;
		
    	if (type == null) {
    		return ResponseEntity.ok(izjavaService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(izjavaService.readPreview(id, type));
    }
	
	@PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin')")
	@PostMapping
	public ResponseEntity<IzjavaInteresovanjaZaVakcinisanje> apply(@RequestBody CreateIzjavaRequest createIzjavaRequest) throws XMLDBException, IOException {
		validate(createIzjavaRequest);
		
		IzjavaInteresovanjaZaVakcinisanje izjava = IzjavaInteresovanjaZaVakcinisanjeFactory.create(createIzjavaRequest);
		
		IzjavaInteresovanjaZaVakcinisanje createdIzjava = izjavaService.create(izjava);
		
		return ResponseEntity.ok(createdIzjava);
	}
	
}
