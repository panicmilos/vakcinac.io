package vakcinac.io.civil.servant.factories;

import java.time.LocalDate;

import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje.InformacijeOPrimanjuVakcine.Proizvodjaci;
import vakcinac.io.civil.servant.models.izj.Tpodnosilac;
import vakcinac.io.core.requests.CreateIzjavaRequest;
import vakcinac.io.core.models.os.Tkontakt;

public class IzjavaInteresovanjaZaVakcinisanjeFactory {
	public static IzjavaInteresovanjaZaVakcinisanje create(CreateIzjavaRequest createIzjavaRequest) {
		IzjavaInteresovanjaZaVakcinisanje izjava = new IzjavaInteresovanjaZaVakcinisanje();
		
		izjava.setDan(LocalDate.now());
				
		Tpodnosilac tPodnosilac = new Tpodnosilac();
		tPodnosilac.setJmbg(createIzjavaRequest.getPodnosilac());
		tPodnosilac.setDrzavljanstvo(createIzjavaRequest.getDrzavljanstvo());
		
		Tkontakt kontakt = new Tkontakt();
		kontakt.setBrojMobilnogTelefona(createIzjavaRequest.getBrojMobilnogTelefona());
		kontakt.setBrojFiksnogTelefona(createIzjavaRequest.getBrojFiksnogTelefona());
		
		IzjavaInteresovanjaZaVakcinisanje.PodnosilacIzjave podnosilac = new IzjavaInteresovanjaZaVakcinisanje.PodnosilacIzjave();
		podnosilac.setPodnosilac(tPodnosilac);
		podnosilac.setKontakt(kontakt);
		podnosilac.setDobrovoljanDavalacKrvi(createIzjavaRequest.isDobrovoljanDavalacKrvi());

		izjava.setPodnosilacIzjave(podnosilac);
		
		Proizvodjaci proizvodjaci = new Proizvodjaci();
		proizvodjaci.getProizvodjac().addAll(createIzjavaRequest.getProizvodjac());
		
		IzjavaInteresovanjaZaVakcinisanje.InformacijeOPrimanjuVakcine informacijeOVakcinisanju = new IzjavaInteresovanjaZaVakcinisanje.InformacijeOPrimanjuVakcine();
		informacijeOVakcinisanju.setOpstina(createIzjavaRequest.getOpstina());
		informacijeOVakcinisanju.setProizvodjaci(proizvodjaci);
		
		return izjava;
	}
}
