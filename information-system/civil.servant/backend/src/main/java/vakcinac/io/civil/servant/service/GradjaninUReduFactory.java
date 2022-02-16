package vakcinac.io.civil.servant.service;

import java.time.LocalDate;

import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.models.red.RedCekanja.GradjaninURedu;

public class GradjaninUReduFactory {
	
	public static GradjaninURedu create(String jmbg, IzjavaInteresovanjaZaVakcinisanje izjava, int waitingDays) {
		GradjaninURedu gradjaninURedu = new GradjaninURedu();
		
		gradjaninURedu.setJmbg(jmbg);
		gradjaninURedu.setMinimalnoVreme(LocalDate.now().plusDays(waitingDays));
		
		GradjaninURedu.Vakcine vakcine = new GradjaninURedu.Vakcine();
		vakcine.getVakcina().addAll(izjava.getInformacijeOPrimanjuVakcine().getProizvodjaci().getProizvodjac());
		
		gradjaninURedu.setVakcine(vakcine);
		
		return gradjaninURedu;

	}

}
