package vakcinac.io.civil.servant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.models.vak.Vakcina;
import vakcinac.io.civil.servant.service.VakcinaService;

@Controller
@RequestMapping("/vakcine")
public class VakcineController {
	
	@Autowired
	private VakcinaService vakcinaService;

	
	@PostMapping
	public ResponseEntity<Vakcina> createVakcina(@RequestBody Vakcina vakcina) {
		
		Vakcina createdVakcina = vakcinaService.create(vakcina.getSerija(), vakcina);
		
		return ResponseEntity.ok(createdVakcina);
	}
	
}
