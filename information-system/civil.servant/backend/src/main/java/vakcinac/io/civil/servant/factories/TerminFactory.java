package vakcinac.io.civil.servant.factories;

import java.time.LocalDateTime;

import vakcinac.io.civil.servant.models.term.Termin;

public class TerminFactory {
	
	public static Termin create(String jmbg, String brojPasosaEbs, String vakcina, LocalDateTime vreme) {
		Termin termin = new Termin();
		
		if (jmbg.isBlank()) {
			termin.setBrojPasosaEbs(brojPasosaEbs);
		} else {
			termin.setJmbg(jmbg);
		}
		
		termin.setVakcina(vakcina);
		termin.setVreme(vreme);

		return termin;
	}

}
