package vakcinac.io.citizen.service;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Vakcinacije;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Vakcinacije.Vakcinacija;
import vakcinac.io.citizen.models.dig.TnosilacSertifikata;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza;
import vakcinac.io.citizen.repository.DigitalniSertifikatRepository;
import vakcinac.io.citizen.repository.jena.CitizenJenaRepository;
import vakcinac.io.core.Constants;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.MissingEntityException;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RandomUtils;

@Service
@RequestScope
public class SertifikatService extends BaseService<DigitalniSertifikat> {
	
	private GradjaninService gradjaninService;
	private PotvrdaService potvrdaService;
	
	@Autowired
	public SertifikatService(DigitalniSertifikatRepository sertifikatRepository, GradjaninService gradjaninService, PotvrdaService potvrdaService, CitizenJenaRepository jenaRepository) {
		super(sertifikatRepository, jenaRepository);
		
		this.gradjaninService = gradjaninService;
		this.potvrdaService = potvrdaService;
	}
	
	@Override
	public DigitalniSertifikat read(String id) {
		return baseRepository.retrieve(id.replace('/', '-'));
	}
	
	@Override
	public DigitalniSertifikat delete(String id) {
		return baseRepository.remove(id.replace('/', '-'));
	}
	
	@Override
	public DigitalniSertifikat create(DigitalniSertifikat sertifikat) throws XMLDBException, IOException {
			
		String id = getValidSertifikatId();
		sertifikat.setBrojSertifikata(id);;
		
		sertifikat.setValidacija(String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id));
		
		String gradjaninId;
		TnosilacSertifikata nosilac = sertifikat.getNosilacSertifikata();
		if (nosilac.getJmbg() != null && !nosilac.getJmbg().isEmpty()) {
			gradjaninId = nosilac.getJmbg();
		}
		else if (nosilac.getBrPasosa() != null && !nosilac.getBrPasosa().isEmpty()) {
			gradjaninId = nosilac.getBrPasosa();
		}
		else if (nosilac.getEbs() != null && !nosilac.getEbs().isEmpty()) {
			gradjaninId = nosilac.getEbs();
		}
		else {
			throw new BadLogicException("Id građanina nije validan.");
		}
		
		fillOutNosilacSertifikata(sertifikat, gradjaninId);
		fillOutRdf(sertifikat, gradjaninId);
		
		return create(id.replace('/', '-'), sertifikat);
	}
	
	private void fillOutRdf(DigitalniSertifikat sertifikat, String gradjaninId) throws XMLDBException, IOException {
		String id = sertifikat.getBrojSertifikata();
		
		sertifikat.setAbout(String.format("%s/digitalni-sertifikat/%s", Constants.ROOT_URL, id));
		sertifikat.setTypeof("rdfos:DigitalniSertifikatDokument");
		
		String za = String.format("%s/gradjani/%s", Constants.ROOT_URL, gradjaninId);
		
		sertifikat.getLink().add(TlinkFactory.create("rdfds:za", za, "rdfos:Gradjanin"));
		
		String zahtev = getRelatedZahtev(za);
        if(zahtev == null || zahtev.trim().isEmpty()) {
            throw new MissingEntityException(String.format("Ne postoji zahtev za digitalni sertifikat za građanina %s", za));
        }	
		sertifikat.getLink().add(TlinkFactory.create("rdfds:naOsnovuZahteva", zahtev, "rdfos:ZahtevZaDigitalniSertifikatDokument"));
		
		String potvrda = getRelatedPotvrda(za);
		if(potvrda == null || potvrda.trim().isEmpty()) {
            throw new MissingEntityException(String.format("Ne postoji potvrda za građanina %s", za));
        }
		sertifikat.getLink().add(TlinkFactory.create("rdfds:saPotvrdom", potvrda, "rdfos:PotvrdaOVakcinisanjuDokument"));
		
		fillOutVakcine(sertifikat, potvrda);
	}
	
	private void fillOutVakcine(DigitalniSertifikat sertifikat, String potvrda) {
		String XQueryExpression = String.format("collection('/db/potvrde')//*:potvrda-o-izvrsenoj-vakcinaciji[@about = '%s']", potvrda);
		
		PotvrdaOIzvrsenojVakcinaciji potvrdaVakc = potvrdaService.findFirstByXQuery(XQueryExpression, PotvrdaOIzvrsenojVakcinaciji.class);
		PodaciOVakcinaciji podaciOVakc = potvrdaVakc.getPodaciOVakcinaciji();
		
		Vakcinacije vakcinacije = new Vakcinacije();
		Integer brojDoza = podaciOVakc.getPodaciODozama().getPrimljenaDoza().size();
		
		vakcinacije.setBrojDoza(new BigInteger(brojDoza.toString()));
		
		for(PrimljenaDoza primljenaDoza : podaciOVakc.getPodaciODozama().getPrimljenaDoza()) {
			Vakcinacija vakcinacija = new Vakcinacija();
			vakcinacija.setDoza(primljenaDoza.getRedniBroj());
			vakcinacija.setZdravstvenaUstanova(podaciOVakc.getZdravstvenaUstanova());
			vakcinacija.setDatum(primljenaDoza.getDatum());
			vakcinacija.setSerija(primljenaDoza.getSerija());
			vakcinacija.setProizvodjac(podaciOVakc.getNazivVakcine());
			vakcinacija.setTip(podaciOVakc.getNazivVakcine());
			
			vakcinacije.getVakcinacija().add(vakcinacija);
		}
	}
	
	private String getRelatedZahtev(String za) {	
		return jenaRepository.readLatestSubject("/zahtevi", String.format("<%s/rdfs/zahtev/za>", Constants.ROOT_URL), String.format("<%s>", za));
	}
	
	private String getRelatedPotvrda(String za) {
		return jenaRepository.readLatestSubject("/potvrde", String.format("<%s/rdfs/potvrda/za>", Constants.ROOT_URL), String.format("<%s>", za));
	}
	
	private void fillOutNosilacSertifikata(DigitalniSertifikat sertifikat, String gradjaninId) {
		Tgradjanin gradjanin = gradjaninService.findById(gradjaninId);
		if (gradjanin == null) {
			throw new MissingEntityException("Građanin sa unesenim id-em ne postoji.");
		}
		
		TnosilacSertifikata nosilac = sertifikat.getNosilacSertifikata();
		nosilac.setIme(gradjanin.getIme());
		nosilac.setPrezime(gradjanin.getPrezime());
		nosilac.setPol(gradjanin.getPol());
		nosilac.setDatumRodjenja(DateUtils.fromXMLToLocalDate(gradjanin.getDatumRodjenja()));
	}
	
	private String getValidSertifikatId() {
        String id = "";

        do {
            id =  generateSertifikatId();
        } while (baseRepository.retrieve(id) != null);

        return id;
    }
	
	 private String generateSertifikatId() {
		 return String.format("%s/%s", RandomUtils.generateRandomNumericString(7), RandomUtils.generateRandomNumericString(2));
	 }

}
