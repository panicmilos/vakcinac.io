package vakcinac.io.citizen.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.citizen.models.izv.IzvestajOImunizaciji;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.citizen.repository.exist.DigitalniSertifikatRepository;
import vakcinac.io.citizen.repository.exist.IzjavaRepository;
import vakcinac.io.citizen.repository.exist.IzvestajRepository;
import vakcinac.io.citizen.repository.exist.PotvrdaRepository;
import vakcinac.io.citizen.repository.exist.SaglasnostRepository;
import vakcinac.io.citizen.repository.exist.ZahtevRepository;
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
	private DigitalniSertifikatRepository digitalniSertifikatRepository;
	
	@Autowired
	private IzjavaRepository izjavaRepository;
	
	@Autowired
	private IzvestajRepository izvestajRepository;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;
	
	@Autowired
	private SaglasnostRepository saglasnostRepository;
	
	@Autowired
	private ZahtevRepository zahtevRepository;

	@Autowired
	private JenaRepository jenaRepository;
	
	private HashMap<Integer, String> documentPaths;
	
	private HashMap<Integer, Class<?>> classRegistry;
	
	
	public TestController() {
		documentPaths = new HashMap<Integer, String>();
		
		String base = Constants.ROOT_RESOURCE + "/data/docs/";
		documentPaths.put(1, base + "digitalni_sertifikat.xml");
		documentPaths.put(2, base + "iskazivanje_interesovanja_za_vakcinisanje.xml");
		documentPaths.put(3, base + "izvestaj_o_imunizaciji.xml");
		documentPaths.put(4, base + "potvrda_o_izvrsenoj_vakcinaciji.xml");
		documentPaths.put(5, base + "saglasnost_za_sprovodjenje_preporucene_imunizacije.xml");
		documentPaths.put(6, base + "zahtev_za_izdavanje_zelenog_sertifikata.xml");
		
		classRegistry = new HashMap<Integer, Class<?>>();
		classRegistry.put(1, DigitalniSertifikat.class);
		classRegistry.put(2, IzjavaInteresovanjaZaVakcinisanje.class);
		classRegistry.put(3, IzvestajOImunizaciji.class);
		classRegistry.put(4, PotvrdaOIzvrsenojVakcinaciji.class);
		classRegistry.put(5, SaglasnostZaSprovodjenjePreporuceneImunizacije.class);
		classRegistry.put(6, ZahtevZaIzdavanjeZelenogSertifikata.class);
	}
	
	@GetMapping(path="exist/{documentNum}", produces=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> TestExistDB(@PathVariable("documentNum") int documentNum, @RequestParam("documentName") String documentName) throws IOException {
		String path = documentPaths.get(documentNum);
		String fileContent = String.join("\n", Files.readAllLines(Paths.get(path)));

		JaxBParser parser = JaxBParserFactory.newInstanceFor(classRegistry.get(documentNum));
		Object deserializedObj = parser.unmarshall(fileContent);
		
		if(documentNum == 1) digitalniSertifikatRepository.store(documentName, (DigitalniSertifikat)deserializedObj);
		if(documentNum == 2) izjavaRepository.store(documentName, (IzjavaInteresovanjaZaVakcinisanje)deserializedObj);
		if(documentNum == 3) izvestajRepository.store(documentName, (IzvestajOImunizaciji)deserializedObj);
		if(documentNum == 4) potvrdaRepository.store(documentName, (PotvrdaOIzvrsenojVakcinaciji)deserializedObj);
		if(documentNum == 5) saglasnostRepository.store(documentName, (SaglasnostZaSprovodjenjePreporuceneImunizacije)deserializedObj);
		if(documentNum == 6) zahtevRepository.store(documentName, (ZahtevZaIzdavanjeZelenogSertifikata)deserializedObj);
		
		if(documentNum == 1) return ResponseEntity.ok(parser.marshall(digitalniSertifikatRepository.retrieve(documentName)));
		if(documentNum == 2) return ResponseEntity.ok(parser.marshall(izjavaRepository.retrieve(documentName)));
		if(documentNum == 3) return ResponseEntity.ok(parser.marshall(izvestajRepository.retrieve(documentName)));
		if(documentNum == 4) return ResponseEntity.ok(parser.marshall(potvrdaRepository.retrieve(documentName)));
		if(documentNum == 5) return ResponseEntity.ok(parser.marshall(saglasnostRepository.retrieve(documentName)));
		if(documentNum == 6) return ResponseEntity.ok(parser.marshall(zahtevRepository.retrieve(documentName)));

		return ResponseEntity.ok(null);
	}
	
	@GetMapping(path="jena/{documentNum}", produces=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> TestJenaDB(@PathVariable("documentNum") int documentNum, @RequestParam("graphUri") String graphUri) throws IOException {
		String path = documentPaths.get(documentNum);
		String fileContent = String.join("\n", Files.readAllLines(Paths.get(path)));

		jenaRepository.insert(fileContent, "/" + graphUri);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (CloseableResultSet set = jenaRepository.read("/" + graphUri)) {
			ResultSetFormatter.outputAsXML(out, set);
		}
		
		return ResponseEntity.ok(new String(out.toByteArray()));
	}
	
	@GetMapping(path="xsl/{documentNum}")
	public ResponseEntity<byte[]> TestXsl(@PathVariable("documentNum") int documentNum, @RequestParam("type") String type) throws IOException {
		String path = documentPaths.get(documentNum);
		String fileContent = String.join("\n", Files.readAllLines(Paths.get(path)));

		JaxBParser parser = JaxBParserFactory.newInstanceFor(classRegistry.get(documentNum));
		Object deserializedObj = parser.unmarshall(fileContent);

		if (type.equals("pdf")) {
			PDFTransformer transformer = new PDFTransformer();
			byte[] pdf = transformer.generate(deserializedObj);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

			
			return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.OK);

		} else {
			XHTMLTransformer transformer = new XHTMLTransformer();
			byte[] xhtml = transformer.generate(deserializedObj);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_XHTML_XML);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			
			return new ResponseEntity<byte[]>(xhtml, headers, HttpStatus.OK);
		}
	}
	
}
