package vakcinac.io.civil.servant.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.service.GradjaninService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.results.doc.CitizenDocumentsResult;

@Controller
@CrossOrigin
@RequestMapping("/gradjani")
public class GradjaniController extends ControllerBase {
	
	private GradjaninService gradjaninService;
	
	@Autowired
	public GradjaniController(ModelMapper mapper, CivilServantValidator validator, GradjaninService gradjaninService) {
		super(mapper, validator);
		this.gradjaninService = gradjaninService;
	}

	@GetMapping("/{id}/documents")
	public ResponseEntity<CitizenDocumentsResult> getDocumentsForGradjanin(@PathVariable("id") String id) throws IOException {
				
		return ResponseEntity.ok(gradjaninService.getDocumentsFor(id));
	}
	
	@GetMapping("/{id}/documents/all")
	public ResponseEntity<CitizenDocumentsResult> getAllDocumentsForGradjanin(@PathVariable("id") String id) throws IOException {
				
		return ResponseEntity.ok(gradjaninService.getAllDocumentsFor(id));
	}
}
