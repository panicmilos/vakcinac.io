package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.citizen.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.core.exceptions.BadLogicException;

@Service
public class ZahtevService {
	
	@Value("${sluzbenik.url}")
	private String sluzbenikUrl;

	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) {		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.postForEntity(String.format("%s/zahtevi", sluzbenikUrl), zahtev, Object.class);
	
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BadLogicException("Nije moguće podneti više od 3 zahteva u roku od nedelju dana ili morate biti vakcinisani bar dva puta.");
		}
		
		return zahtev;

	}
}
