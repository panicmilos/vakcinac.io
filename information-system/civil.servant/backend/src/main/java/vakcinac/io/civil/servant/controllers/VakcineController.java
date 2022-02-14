package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;
import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.requests.AddVakcinaStockRequest;
import vakcinac.io.civil.servant.requests.CreateVakcinaRequest;
import vakcinac.io.civil.servant.service.StanjeVakcinaService;
import vakcinac.io.civil.servant.service.VakcinaService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping("/vakcine")
public class VakcineController extends ControllerBase {
	
	@Autowired
	private VakcinaService vakcinaService;
	
	@Autowired
	private StanjeVakcinaService stanjeVakcinaService;
	
	@Autowired
	public VakcineController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}

	
	@PostMapping
	public ResponseEntity<Vakcina> createVakcina(@RequestBody CreateVakcinaRequest createVakcinaRequest) {
//		validate(createVakcinaRequest);

		Vakcina vakcina = (Vakcina) map(createVakcinaRequest, Vakcina.class);
		
		Vakcina createdVakcina = vakcinaService.create(vakcina);
		
		return ResponseEntity.ok(createdVakcina);
	}
	
	@PutMapping("/{vaccineId}/stock")
	public ResponseEntity<StanjeVakcina.StanjeVakcine> changeStock(@PathVariable("vaccineId") String vaccineId, @RequestBody AddVakcinaStockRequest addVakcinaStockRequest) throws XMLDBException {
//		validate(changeVakcinaStockRequest);
		
		return ResponseEntity.ok(stanjeVakcinaService.addToStockFor(vaccineId, addVakcinaStockRequest.getAmount()));
	

		
	}
	
}
