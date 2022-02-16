package vakcinac.io.civil.servant.service;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.civil.servant.models.zrad.ZdravstveniRadnik;
import vakcinac.io.civil.servant.repository.ZdravstveniRadnikRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class ZdravstveniRadnikService extends BaseService<ZdravstveniRadnik> {
	
	public ZdravstveniRadnikService(ZdravstveniRadnikRepository zdravstveniRadnikRepository, CivilServantJenaRepository jenaRepository) {
		super(zdravstveniRadnikRepository, jenaRepository);
	}
	
	public ZdravstveniRadnik create(ZdravstveniRadnik zdravstveniRadnik) throws IOException {
		String id = zdravstveniRadnik.getJmbg();
		
		fillOutRdf(id, zdravstveniRadnik);
		
	    JaxBParser parser = JaxBParserFactory.newInstanceFor(ZdravstveniRadnik.class);
	    String serializedObj = parser.marshall(zdravstveniRadnik);
	    
	    jenaRepository.insert(serializedObj, "/zdravstveni-radnici");
		
		return create(id, zdravstveniRadnik);
	}
	
	private void fillOutRdf(String id, ZdravstveniRadnik zdravstveniRadnik) {
		zdravstveniRadnik.setAbout(String.format("%s/zdravstveni-radnici/%s", Constants.ROOT_URL, id));
		zdravstveniRadnik.setTypeof("rdfos:ZdravstveniRadnik");
		
		String datumRodjenja = DateUtils.fromXMLToString(zdravstveniRadnik.getDatumRodjenja());
		
		zdravstveniRadnik.getMeta().add(TmetaFactory.create("rdfos:saJMBG", "xsd:string", zdravstveniRadnik.getJmbg()));
		zdravstveniRadnik.getMeta().add(TmetaFactory.create("rdfos:seZove", "xsd:string", zdravstveniRadnik.getIme()));
		zdravstveniRadnik.getMeta().add(TmetaFactory.create("rdfos:seRodio", "xsd:date", datumRodjenja));
		zdravstveniRadnik.getMeta().add(TmetaFactory.create("rdfos:jePola", "xsd:int", Integer.toString(zdravstveniRadnik.getPol())));
		
		addGeneralAttributes(zdravstveniRadnik);
	}
	
	private void addGeneralAttributes(ZdravstveniRadnik zdravstveniRadnik) {
		zdravstveniRadnik.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		zdravstveniRadnik.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		zdravstveniRadnik.getOtherAttributes().put(QName.valueOf("xmlns:rdfzr"), "https://www.vakcinac-io.rs/rdfs/zdravstveni-radnik/");
	}
	
	public ZdravstveniRadnik findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/zdravstveni-radnici')//*:zdravstveni-radnik/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, ZdravstveniRadnik.class);
	}
	
	public ZdravstveniRadnik findByEmail(String email) {
		String XQueryExpression = String.format("collection('/db/zdravstveni-radnici')//*:zdravstveni-radnik/*:email[text() = '%s']/..", email);
		
		return findFirstByXQuery(XQueryExpression, ZdravstveniRadnik.class);
	}
}
