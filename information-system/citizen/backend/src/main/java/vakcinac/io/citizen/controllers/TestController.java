package vakcinac.io.citizen.controllers;

import java.io.File;

import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.repository.exist.SaglasnostRepository;
import vakcinac.io.citizen.repository.jena.CloseableResultSet;
import vakcinac.io.citizen.repository.jena.JenaRepository;
import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;
import vakcinac.io.citizen.utils.transformers.PDFTransformer;
import vakcinac.io.citizen.utils.transformers.XHTMLTransformer;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	SaglasnostRepository saglasnostRepository;

	@Autowired
	JenaRepository jenaRepository;

	@GetMapping("2")
	public ResponseEntity<Object> Test2() {

		try {
			JaxBParser parser = JaxBParserFactory.newInstanceFor(SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
			SaglasnostZaSprovodjenjePreporuceneImunizacije szspi = parser.unmarshall(new File(
					Constants.ROOT_RESOURCE + "/data/docs/saglasnost_za_sprovodjenje_preporucene_imunizacije.xml"));

			saglasnostRepository.store("cao.xml", szspi);
			return ResponseEntity.ok(saglasnostRepository.retrieve("cao.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);

	}

	@GetMapping("3")
	public ResponseEntity<byte[]> Test3() {

		XHTMLTransformer transformer = new XHTMLTransformer();
		SaglasnostZaSprovodjenjePreporuceneImunizacije szspi = saglasnostRepository.retrieve("cao.xml");

		byte[] xhtml = transformer.generate(szspi);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XHTML_XML);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<byte[]>(xhtml, headers, HttpStatus.OK);

	}

	@GetMapping("4")
	public ResponseEntity<byte[]> Test4() {

		PDFTransformer transformer = new PDFTransformer();
		SaglasnostZaSprovodjenjePreporuceneImunizacije szspi = saglasnostRepository.retrieve("cao.xml");

		byte[] pdf = transformer.generate(szspi);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.OK);
	}

	@GetMapping("5")
	public ResponseEntity<String> Test5() throws Exception {

		try (CloseableResultSet set = jenaRepository.read("/nesto/cao")) {
			ResultSetFormatter.outputAsXML(System.out, set);
		}

		return ResponseEntity.ok("");
	}

	@GetMapping("6")
		public ResponseEntity<String> Test6() throws Exception {
	
	//		jenaRepository.dropGraph("/1234");
		jenaRepository.dropAll();
	
			return ResponseEntity.ok("");
		}
}
