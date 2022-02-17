package vakcinac.io.citizen.service;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.citizen.models.sgradj.StraniGradjanin;
import vakcinac.io.citizen.repository.StraniGradjaninRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
@RequestScope
public class StraniGradjaninService extends BaseService<StraniGradjanin> {
	
	public StraniGradjaninService(StraniGradjaninRepository straniGradjaninRepository, CitizenJenaRepository jenaRepository) {
		super(straniGradjaninRepository, jenaRepository);
	}
	
	public StraniGradjanin create(StraniGradjanin straniGradjanin) throws IOException {
		validate(straniGradjanin);
		
		String id = straniGradjanin.getIdentifikacioniDokument() == 0 ? straniGradjanin.getBrojPasosa() : straniGradjanin.getEbs();
		
		fillOutRdf(id, straniGradjanin);
		
	    JaxBParser parser = JaxBParserFactory.newInstanceFor(StraniGradjanin.class);
	    String serializedObj = parser.marshall(straniGradjanin);
	    
	    jenaRepository.insert(serializedObj, "/gradjani");
		
		return create(id, straniGradjanin);
	}
	
	private void validate(StraniGradjanin straniGradjanin) {
		boolean pasosNotEmpty = StringUtils.notNullOrEmpty(straniGradjanin.getBrojPasosa());
		boolean ebsNotEmpty = StringUtils.notNullOrEmpty(straniGradjanin.getEbs());
		
		if (pasosNotEmpty && ebsNotEmpty) {
			throw new BadLogicException("Moguće je uneti samo jedan dokument: pasoš ili ebs.");
		}
	}
	
	private void fillOutRdf(String id, StraniGradjanin straniGradjanin) {
		straniGradjanin.setAbout(String.format("%s/gradjani/%s", Constants.ROOT_URL, id));
		straniGradjanin.setTypeof("rdfos:StraniGradjanin");
		
		String datumRodjenja = DateUtils.fromXMLToString(straniGradjanin.getDatumRodjenja());
		
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfos:seZove", "xsd:string", straniGradjanin.getIme()));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfos:seRodio", "xsd:date", datumRodjenja));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfos:jePola", "xsd:int", Integer.toString(straniGradjanin.getPol())));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfsg:saId", "xsd:string", id));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfsg:imaRoditelja", "xsd:string", straniGradjanin.getImeRoditelja()));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfsg:izDrzave", "xsd:string", straniGradjanin.getDrzava()));
		straniGradjanin.getMeta().add(TmetaFactory.create("rdfsg:boraviURS", "xsd:boolean", Boolean.toString(straniGradjanin.isBoravakURs())));
		
		addGeneralAttributes(straniGradjanin);
	}
	
	private void addGeneralAttributes(StraniGradjanin straniGradjanin) {
		straniGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		straniGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		straniGradjanin.getOtherAttributes().put(QName.valueOf("xmlns:rdfsg"), "https://www.vakcinac-io.rs/rdfs/strani-gradjanin/");
	}
	
	public StraniGradjanin findByKorisnickoIme(String korisnickoIme) {
		String XQueryExpression = String.format("collection('/db/strani-gradjani')//*:strani-gradjanin/*:korisnicko-ime[text() = '%s']/..", korisnickoIme);
		
		return findFirstByXQuery(XQueryExpression, StraniGradjanin.class);
	}
	
	public StraniGradjanin findByEmail(String email) {
		String XQueryExpression = String.format("collection('/db/strani-gradjani')//*:strani-gradjanin/*:email[text() = '%s']/..", email);
		
		return findFirstByXQuery(XQueryExpression, StraniGradjanin.class);
	}
}
