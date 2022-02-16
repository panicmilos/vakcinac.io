package vakcinac.io.civil.servant.controllers;

import org.apache.jena.query.QuerySolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.civil.servant.service.RedCekanjaService;
import vakcinac.io.civil.servant.service.TerminService;
import vakcinac.io.core.repository.jena.CloseableResultSet;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	RedCekanjaService redCekanja;
	
	@Autowired
	TerminService termin;

	@Autowired
	CivilServantJenaRepository jena;
	
    @GetMapping(path="proxy-test")
    public ResponseEntity<String> testCoreString() {
        return ResponseEntity.ok("Benny the Butcher");
    }

//    @PostMapping()
    public ResponseEntity<?> test() throws Exception {
//		RedCekanja.GradjaninURedu gradjaninURedu = new RedCekanja.GradjaninURedu();
//		gradjaninURedu.setJmbg("214124412122");
//		gradjaninURedu.setMinimalnoVreme(LocalDate.now().plusDays(6));
//		
//		Vakcine vakcine = new Vakcine();
//		vakcine.getVakcina().add(2);
//		gradjaninURedu.setVakcine(vakcine);
//		
//		
//		redCekanja.add(gradjaninURedu);
//		redCekanja.remove(1);
//		Termin termin = this.termin.findAvaiableTermin(gradjaninURedu);
    	
    	redCekanja.tryToAssignTermins();
		
		return ResponseEntity.ok(null);

    }
    
    @PostMapping
    public ResponseEntity<?> test2412() throws Exception {
    	
    	
		
		System.out.println(redCekanja.isInRow("214124412121"));
		System.out.println(redCekanja.isInRow("214124412122"));
		
		System.out.println(termin.hasActiveTermin("214124412124"));
		System.out.println(termin.hasActiveTermin("214124412126"));

		return ResponseEntity.ok(null);

    }
}
