package vakcinac.io.citizen.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.repository.DigitalniSertifikatRepository;
import vakcinac.io.citizen.repository.PotvrdaRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.CoreClass;
import vakcinac.io.core.repository.exist.CloseableResource;
import vakcinac.io.core.repository.jena.RdfObject;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;
import vakcinac.io.core.utils.transformers.PDFTransformer;
import vakcinac.io.core.utils.transformers.XHTMLTransformer;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private DigitalniSertifikatRepository digitalniSertifikatRepository;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;

	@Autowired
	private CitizenJenaRepository jenaRepository;
	
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
		classRegistry.put(4, PotvrdaOIzvrsenojVakcinaciji.class);
	}
	
	@GetMapping(path="xquery")
	public ResponseEntity<String> testCoreStrin2g() throws XMLDBException, IOException {
		ResourceIterator iterator =  digitalniSertifikatRepository.retrieveUsingXQuery("for $document in collection('/db/digitalni-sertifikati') return $document");
		
        try (CloseableResource resource = new CloseableResource()) {
	        while(iterator.hasMoreResources()) {
        		resource.setRealResource(iterator.nextResource());
                System.out.println(resource.getContent());
	        }
        }
        
		iterator = digitalniSertifikatRepository.retrieveUsingXQuery(Constants.ROOT_RESOURCE + "/data/xquery/count.xqy", "/db/digitalni-sertifikati", "kt3");
		
        try (CloseableResource resource = new CloseableResource()) {
	        while(iterator.hasMoreResources()) {
        		resource.setRealResource(iterator.nextResource());
                System.out.println(resource.getContent());
	        }
        }


		return null;
	}
	
	@GetMapping(path="xqueryupdate")
	public ResponseEntity<String> testCoreStrin2412g() throws XMLDBException, IOException {

//		digitalniSertifikatRepository.insertBefore("kt3new.xml", "//br-pasosa", "<a>cap</a>");
//		digitalniSertifikatRepository.insertAfter("kt3new.xml", "//br-pasosa", "<a>cap2</a>");

//		digitalniSertifikatRepository.append("kt3new.xml", "//nosilac-sertifikata", "<a>mrs u pm</a>");
//		digitalniSertifikatRepository.update("kt3new.xml", "//br-pasosa", "<a>nesto sada kao radi?</a>");

		digitalniSertifikatRepository.remove("kt3new.xml", "//br-pasosa");

		return null;
	}
 

	@GetMapping(path="common")
	public ResponseEntity<String> testCoreString() {
		System.out.println(CoreClass.coreString);
		return ResponseEntity.ok(CoreClass.coreString);
	}

	@GetMapping(path="exist/{documentNum}", produces=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> TestExistDB(@PathVariable("documentNum") int documentNum, @RequestParam("documentName") String documentName) throws IOException {
		String path = documentPaths.get(documentNum);
		String fileContent = String.join("\n", Files.readAllLines(Paths.get(path)));

		JaxBParser parser = JaxBParserFactory.newInstanceFor(classRegistry.get(documentNum));
		Object deserializedObj = parser.unmarshall(fileContent);
		
		if(documentNum == 1) digitalniSertifikatRepository.store(documentName, (DigitalniSertifikat)deserializedObj);
		if(documentNum == 4) potvrdaRepository.store(documentName, (PotvrdaOIzvrsenojVakcinaciji)deserializedObj);
		
		if(documentNum == 1) return ResponseEntity.ok(parser.marshall(digitalniSertifikatRepository.retrieve(documentName)));
		if(documentNum == 4) return ResponseEntity.ok(parser.marshall(potvrdaRepository.retrieve(documentName)));

		return ResponseEntity.ok(null);
	}
	
	@GetMapping(path="jena/{documentNum}", produces=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> TestJenaDB(@PathVariable("documentNum") int documentNum, @RequestParam("graphUri") String graphUri) throws IOException {
		String path = documentPaths.get(documentNum);
		String fileContent = String.join("\n", Files.readAllLines(Paths.get(path)));

		jenaRepository.insert(fileContent, "/" + graphUri);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	 RdfObject m = jenaRepository.construct("/" + graphUri, Constants.ROOT_RESOURCE + "/data/sparql/construct.sparql", "http://localhost:3030/CitizenDataset/data/kt3");
		m.toString("RDF/JSON");
		
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

	@PostMapping(path = "xsl/generate/pdf", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> generatePdf(@RequestBody String xmlDocument) {
		StringReader stringReader = new StringReader(xmlDocument);
		DigitalniSertifikat digitalniSertifikat = JAXB.unmarshal(stringReader, DigitalniSertifikat.class);

		PDFTransformer transformer = new PDFTransformer();
		byte[] pdf = transformer.generate(digitalniSertifikat);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.OK);
	}

	@GetMapping(path = "proxy-test")
	public ResponseEntity<String> testProxy() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> proxyResponse = restTemplate.getForEntity("http://localhost:8881/test/proxy-test", String.class);
		return proxyResponse;
	}
	
}
