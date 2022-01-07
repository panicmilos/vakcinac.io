package vakcinac.io.citizen.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;

@Controller
@RequestMapping("/test")
public class TestController {

	@GetMapping
	public ResponseEntity<Object> Test() throws IOException {

		System.out.println("[INFO] Example 1: JAXB unmarshalling.\n");

		String x = String.join("", Files.readAllLines(Paths.get(Constants.ROOT_RESOURCE + "/data/docs/digitalni_sertifikat.xml")));

		// Unmarshalling generiše objektni model na osnovu XML fajla
		JaxBParser parser = JaxBParserFactory.newInstanceFor(DigitalniSertifikat.class);
		
		DigitalniSertifikat ds = parser.unmarshall(x);

		return ResponseEntity.ok(ds);

	}

}
