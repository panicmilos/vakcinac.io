package vakcinac.io.citizen.service;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.citizen.models.dgradj.DomaciGradjanin;
import vakcinac.io.citizen.repository.DomaciGradjaninRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class DomaciGradjaninService extends BaseService<DomaciGradjanin> {
	
	public DomaciGradjaninService(DomaciGradjaninRepository domaciGradjaninRepository, CitizenJenaRepository jenaRepository) {
		super(domaciGradjaninRepository, jenaRepository);
	}
	
	public DomaciGradjanin create(DomaciGradjanin domaciGradjanin) throws IOException {
		String id = domaciGradjanin.getJmbg();
		
		fillOutRdf(id, domaciGradjanin);
		
	    JaxBParser parser = JaxBParserFactory.newInstanceFor(DomaciGradjanin.class);
	    String serializedObj = parser.marshall(domaciGradjanin);
	    
	    jenaRepository.insert(serializedObj, "/gradjani");
		
		return create(id, domaciGradjanin);
	}
	
	private void fillOutRdf(String id, DomaciGradjanin domaciGradjanin) {
		domaciGradjanin.setAbout(String.format("%s/gradjani/%s", Constants.ROOT_URL, id));
		domaciGradjanin.setTypeof("rdfos:DomaciGradjanin");
		
		String datumRodjenja = DateUtils.fromXMLToString(domaciGradjanin.getDatumRodjenja());
		
		domaciGradjanin.getMeta().add(TmetaFactory.create("rdfos:saJMBG", "xsd:string", domaciGradjanin.getJmbg()));
		domaciGradjanin.getMeta().add(TmetaFactory.create("rdfos:seZove", "xsd:string", domaciGradjanin.getIme()));
		domaciGradjanin.getMeta().add(TmetaFactory.create("rdfos:seRodio", "xsd:date", datumRodjenja));
		domaciGradjanin.getMeta().add(TmetaFactory.create("rdfos:jePola", "xsd:int", Integer.toString(domaciGradjanin.getPol())));
		domaciGradjanin.getMeta().add(TmetaFactory.create("rdfdg:imaRoditelja", "xsd:string", domaciGradjanin.getImeRoditelja()));
		
		addGeneralAttributes(domaciGradjanin);
	}
	
	private void addGeneralAttributes(DomaciGradjanin domaciGradjanin) {
		domaciGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		domaciGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		domaciGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:rdfdg"), "https://www.vakcinac-io.rs/rdfs/domaci-gradjanin/");
	}
	
	public DomaciGradjanin findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/domaci-gradjani')//*:domaci-gradjanin/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, DomaciGradjanin.class);
	}
}
