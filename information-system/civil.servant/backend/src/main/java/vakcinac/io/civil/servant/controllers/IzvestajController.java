package vakcinac.io.civil.servant.controllers;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.civil.servant.models.izv.IzvestajOImunizaciji;
import vakcinac.io.civil.servant.service.IzvestajService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;

@Controller
@RequestMapping(path = "izvestaji", produces = { "application/xml" })
public class IzvestajController extends ControllerBase {

	@Autowired
	private IzvestajService izvestajService;
	
	@Autowired
	public IzvestajController(ModelMapper mapper, CivilServantValidator validator) {
		super(mapper, validator);
	}
	
	@GetMapping("/{id}/preview")
    public ResponseEntity<?> preview(@PathVariable String id, @RequestParam(required = false) String type) throws Exception {
		
    	if (type == null) {
    		return ResponseEntity.ok(izvestajService.readPlain(id));
    	}
    	
    	return ResponseEntity.ok(izvestajService.readPreview(id, type));
    }

	@GetMapping
	public ResponseEntity<IzvestajOImunizaciji> make(@RequestParam(name="startDate") String startDateS, @RequestParam(name="endDate") String endDateS) throws Exception {
		
		LocalDate startDate = LocalDate.parse(startDateS);
		LocalDate endDate = LocalDate.parse(endDateS);
		
		IzvestajOImunizaciji izvestaj = izvestajService.generate(startDate, endDate);
		
		izvestajService.create(izvestaj);
		
		return ResponseEntity.ok(izvestaj);
	}
	
}
