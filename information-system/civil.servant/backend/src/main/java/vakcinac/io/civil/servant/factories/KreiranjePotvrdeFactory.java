package vakcinac.io.civil.servant.factories;

import vakcinac.io.civil.servant.models.pot.KreiranjePotvrde;

public class KreiranjePotvrdeFactory {

	public static KreiranjePotvrde create(String jmbg, String naziv, String serija) {
		KreiranjePotvrde potvrda = new KreiranjePotvrde();
		
		potvrda.setJmbgOsobe(jmbg);
		potvrda.setNazivVakcine(naziv);
		potvrda.setSerija(serija);
		
		return potvrda;
	}
}
