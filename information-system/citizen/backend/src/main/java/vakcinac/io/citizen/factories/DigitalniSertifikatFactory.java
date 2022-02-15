package vakcinac.io.citizen.factories;

import java.time.LocalDateTime;

import vakcinac.io.citizen.models.dig.DigitalniSertifikat;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Vakcinacije;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Testovi;
import vakcinac.io.citizen.models.dig.DigitalniSertifikat.Testovi.Test;
import vakcinac.io.citizen.models.dig.TnosilacSertifikata;
import vakcinac.io.core.Constants;
import vakcinac.io.core.factories.TlinkFactory;
import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.core.requests.helpers.SertifikatTest;

public class DigitalniSertifikatFactory {
	public static DigitalniSertifikat create(CreateSertifikatRequest createSertifikatRequest) {
		DigitalniSertifikat sertifikat = new DigitalniSertifikat();
		
		sertifikat.setDatumIzdavanja(LocalDateTime.now());
		sertifikat.setVakcinacije(new Vakcinacije());
		
		TnosilacSertifikata nosilac = new TnosilacSertifikata();
		String id = createSertifikatRequest.getGradjaninId();
		Integer identification = createSertifikatRequest.getCitizenIdentification();
		if (identification == 0) {
			nosilac.setJmbg(id);
		}
		else if (identification == 1) {
			nosilac.setBrPasosa(id);
		}
		else {
			nosilac.setEbs(id);
		}		
		sertifikat.setNosilacSertifikata(nosilac);
		
		Testovi testovi = new Testovi();
		sertifikat.setTestovi(testovi);
		
		testovi.setBrojTestova(createSertifikatRequest.getBrojTestova());
		for(SertifikatTest sertTest : createSertifikatRequest.getTestovi()) {
			Test test = new Test();
			
			test.setIme(sertTest.getIme());
			test.setVrstaUzorka(sertTest.getVrstaUzorka());
			test.setProizvodjac(sertTest.getProizvodjac());
			test.setDatumUzorka(sertTest.getDatumUzorka());
			test.setDatumIzdavanja(sertTest.getDatumIzdavanja());
			test.setRezultat(sertTest.getRezultat());
			test.setLabaratorija(sertTest.getLabaratorija());
			test.setBroj(sertTest.getBroj());
			
			testovi.getTest().add(test);
		}
		
		String izdao = String.format("%s/sluzbenici/%s", Constants.ROOT_URL, createSertifikatRequest.getSluzbenikId());
		sertifikat.getLink().add(TlinkFactory.create("rdfds:izdao", izdao, "rdfos:Sluzbenik"));
		
		return sertifikat;
	}
}
