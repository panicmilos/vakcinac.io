package vakcinac.io.citizen.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.repository.DigitalniSertifikatiRepository;
import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	DigitalniSertifikatiRepository test;
	
	@GetMapping()
	public ResponseEntity<Object> Test() throws IOException {

		System.out.println("[INFO] Example 1: JAXB unmarshalling.\n");

		// Unmarshalling generiše objektni model na osnovu XML fajla
		JaxBParser parser = JaxBParserFactory.newInstanceFor(DigitalniSertifikat.class);

		DigitalniSertifikat ds = parser.unmarshall(new File(Constants.ROOT_RESOURCE + "/data/docs/digitalni_sertifikat.xml"));

		return ResponseEntity.ok(ds);

	}

	@GetMapping("2")
	public ResponseEntity<Object> Test2() {

		try {
			JaxBParser parser = JaxBParserFactory.newInstanceFor(DigitalniSertifikat.class);
			DigitalniSertifikat ds = parser.unmarshall(new File(Constants.ROOT_RESOURCE + "/data/docs/digitalni_sertifikat.xml"));


			test.store("cao.xml", ds);			
			return ResponseEntity.ok(test.retrieve("cao.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);

	}


}
