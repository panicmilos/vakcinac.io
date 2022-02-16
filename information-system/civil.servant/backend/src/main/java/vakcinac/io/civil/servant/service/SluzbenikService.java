package vakcinac.io.civil.servant.service;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.civil.servant.models.sluz.Sluzbenik;
import vakcinac.io.civil.servant.repository.SluzbenikRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class SluzbenikService extends BaseService<Sluzbenik> {
	
	public SluzbenikService(SluzbenikRepository sluzbenikRepository, CivilServantJenaRepository jenaRepository) {
		super(sluzbenikRepository, jenaRepository);
	}
	
	public Sluzbenik create(Sluzbenik sluzbenik) throws IOException {
		String id = sluzbenik.getJmbg();
		
		fillOutRdf(id, sluzbenik);
		
	    JaxBParser parser = JaxBParserFactory.newInstanceFor(Sluzbenik.class);
	    String serializedObj = parser.marshall(sluzbenik);
	    
	    jenaRepository.insert(serializedObj, "/sluzbenici");
		
		return create(id, sluzbenik);
	}
	
	private void fillOutRdf(String id, Sluzbenik sluzbenik) {
		sluzbenik.setAbout(String.format("%s/sluzbenici/%s", Constants.ROOT_URL, id));
		sluzbenik.setTypeof("rdfos:Sluzbenik");
		
		String datumRodjenja = DateUtils.fromXMLToString(sluzbenik.getDatumRodjenja());
		
		sluzbenik.getMeta().add(TmetaFactory.create("rdfos:saJMBG", "xsd:string", sluzbenik.getJmbg()));
		sluzbenik.getMeta().add(TmetaFactory.create("rdfos:seZove", "xsd:string", sluzbenik.getIme()));
		sluzbenik.getMeta().add(TmetaFactory.create("rdfos:seRodio", "xsd:date", datumRodjenja));
		sluzbenik.getMeta().add(TmetaFactory.create("rdfos:jePola", "xsd:int", Integer.toString(sluzbenik.getPol())));
		
		addGeneralAttributes(sluzbenik);
	}
	
	private void addGeneralAttributes(Sluzbenik sluzbenik) {
		sluzbenik.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		sluzbenik.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		sluzbenik.getOtherAttributes().put(QName.valueOf("xmlns:rdfsl"), "https://www.vakcinac-io.rs/rdfs/sluzbenik/");
	}
	
	public Sluzbenik findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/sluzbenici')//*:sluzbenik/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, Sluzbenik.class);
	}
}
