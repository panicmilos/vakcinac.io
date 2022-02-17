package vakcinac.io.civil.servant.factories;

import vakcinac.io.civil.servant.models.pot.DodajDozu;

public class DodajDozuFactory {

	public static DodajDozu create(String jmbg, String serija) {
		DodajDozu doza = new DodajDozu();
		
		doza.setJmbg(jmbg);
		doza.setSerija(serija);
		
		return doza;
	}
}
