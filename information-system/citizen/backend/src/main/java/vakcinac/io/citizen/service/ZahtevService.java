package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;

@Service
public class ZahtevService {
	
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;

	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) {		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(String.format("%s/zahtevi", sluzbenikUrl), zahtev, ZahtevZaIzdavanjeZelenogSertifikata.class);
	
		return zahtev;

	}
}
