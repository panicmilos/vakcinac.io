package vakcinac.io.civil.servant.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import vakcinac.io.core.models.os.InformacijeOPrimljenimDozamaIzPotvrde;
import vakcinac.io.core.results.agres.AggregateResult;

@Service
@RequestScope
public class PotvrdaService {

	@Value("${gradjanin.url}")
	private String gradjaninUrl;

	public AggregateResult aggregateByDoses(LocalDate startDate, LocalDate endDate) {
        return aggregate("doses", startDate, endDate);
    }
	
	public AggregateResult aggregateByTypes(LocalDate startDate, LocalDate endDate) {
        return aggregate("types", startDate, endDate);
    }
	
	private AggregateResult aggregate(String by, LocalDate startDate, LocalDate endDate) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AggregateResult> response = restTemplate.getForEntity(String.format("%s/potvrde/aggregate/%s?startDate=%s&endDate=%s", gradjaninUrl, by, startDate, endDate), AggregateResult.class);

        return response.getBody();
    }

    public InformacijeOPrimljenimDozamaIzPotvrde getVakcine(String gradjaninId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<InformacijeOPrimljenimDozamaIzPotvrde> response = restTemplate.getForEntity(String.format("%s/potvrde/gradjanin/%s/doze", gradjaninUrl, gradjaninId), InformacijeOPrimljenimDozamaIzPotvrde.class);

        return response.getBody();
    }

    public int getNumberOfVakcine(String gradjaninId) {
    	return getVakcine(gradjaninId).getPrimljenaDozaIzPotvrde().size();
    }
}
