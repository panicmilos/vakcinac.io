package vakcinac.io.civil.servant.service;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.civil.servant.repository.ZahtevRepository;
import vakcinac.io.civil.servant.repository.jena.CivilServantJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.factories.TmetaFactory;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

@Service
public class ZahtevService extends BaseService<ZahtevZaIzdavanjeZelenogSertifikata> {

	private GradjaninService gradjaninService;
	
	public ZahtevService(GradjaninService gradjaninService, ZahtevRepository zahtevRepository, CivilServantJenaRepository jenaRepository) {
		super(zahtevRepository, jenaRepository);
		
		this.gradjaninService = gradjaninService;
	}

	@Override
	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) throws Exception {
		
		String jmbg = zahtev.getPodnosilacZahteva().getJmbg();
		Tgradjanin gradjanin = gradjaninService.read(jmbg);
		
		fillRestOfZahtev(zahtev, gradjanin);
		fillRdfOfZahtev(zahtev);
		
		JaxBParser parser = JaxBParserFactory.newInstanceFor(ZahtevZaIzdavanjeZelenogSertifikata.class);
		String serializedObj = parser.marshall(zahtev);
		
		jenaRepository.insert(serializedObj, "/zahtevi");
		
		String id = String.format("%s_%d", jmbg, baseRepository.count(jmbg) + 1);
		
		return create(id, serializedObj);
	}
	
	private void fillRestOfZahtev(ZahtevZaIzdavanjeZelenogSertifikata zahtev, Tgradjanin gradjanin) {
		zahtev.getPodnosilacZahteva().setIme(gradjanin.getIme());
		zahtev.getPodnosilacZahteva().setPrezime(gradjanin.getPrezime());
		zahtev.getPodnosilacZahteva().setPol(gradjanin.getPol());
		zahtev.getPodnosilacZahteva().setDatumRodjenja(LocalDateUtils.from(gradjanin.getDatumRodjenja()));
		
		// TODO: DODATI PASOS
	}
	
	private void fillRdfOfZahtev(ZahtevZaIzdavanjeZelenogSertifikata zahtev) throws XMLDBException, IOException {
		String jmbg = zahtev.getPodnosilacZahteva().getJmbg();

		zahtev.setAbout(String.format("%s/zahtev/%s/%d", Constants.ROOT_URL, jmbg, baseRepository.count(jmbg) + 1));
		zahtev.setTypeof("rdfos:ZahtevZaDigitalniSertifikatDokument");
		
		zahtev.getMeta().add(TmetaFactory.create("rdfos:izdat", "xsd:date", zahtev.getDan().toString()));
		
		zahtev.getLink().add(TlinkFactory.create("rdfzzizs:za", String.format("%s/gradjani/%s", Constants.ROOT_URL, jmbg), "rdfos:Gradjanin"));
		
		zahtev.getOtherAttributes().put(QName.valueOf("xmlns:xsd"), "http://www.w3.org/2001/XMLSchema#");
		zahtev.getOtherAttributes().put(QName.valueOf("xmlns:rdfos"), "https://www.vakcinac-io.rs/rdfs/deljeno/");
		zahtev.getOtherAttributes().put(QName.valueOf("xmlns:rdfzzizs"), "https://www.vakcinac-io.rs/rdfs/zahtev/");
		
		//TODO: NEkako ucitati sluzbenika?
		
	}
	
}
