package vakcinac.io.civil.servant.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.red.RedCekanja;
import vakcinac.io.civil.servant.models.red.RedCekanja.GradjaninURedu.Vakcine;
import vakcinac.io.civil.servant.service.RedCekanjaService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	RedCekanjaService redCekanja;

    @GetMapping(path="proxy-test")
    public ResponseEntity<String> testCoreString() {
        return ResponseEntity.ok("Benny the Butcher");
    }

    @PostMapping
    public ResponseEntity<?> test() throws XMLDBException {
//		RedCekanja.GradjaninURedu gradjaninURedu = new RedCekanja.GradjaninURedu();
//		gradjaninURedu.setJmbg("214124412124");
//		gradjaninURedu.setMinimalnoVreme(LocalDate.now());
//		
//		Vakcine vakcine = new Vakcine();
//		vakcine.getVakcina().add(2);
//		gradjaninURedu.setVakcine(vakcine);
//		redCekanja.add(gradjaninURedu);
		redCekanja.remove(1);
		
		return ResponseEntity.ok(null);

    }
}
