package vakcinac.io.civil.servant.factories;

import java.time.LocalDate;

import vakcinac.io.civil.servant.models.zah.TpodnosilacZahteva;
import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.core.requests.CreateZahtevRequest;

public class ZahtevZaIzdavanjeZelenogSertifikataFactory {
	
	public static ZahtevZaIzdavanjeZelenogSertifikata create(CreateZahtevRequest createZahtevRequest) {
		ZahtevZaIzdavanjeZelenogSertifikata zahtev = new ZahtevZaIzdavanjeZelenogSertifikata();
		
		zahtev.setDan(LocalDate.now());
		zahtev.setMesto(createZahtevRequest.getMesto());
		
		TpodnosilacZahteva podnosilac = new TpodnosilacZahteva();
		podnosilac.setJmbg(createZahtevRequest.getPodnosilac());
		podnosilac.setBrojPasosa(createZahtevRequest.getPasos());
		
		zahtev.setPodnosilacZahteva(podnosilac);
		
		zahtev.setRazlog(createZahtevRequest.getRazlog());
		
		return zahtev;
	}
}
