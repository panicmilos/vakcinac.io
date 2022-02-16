package vakcinac.io.civil.servant.factories;

import java.math.BigInteger;
import java.time.LocalDate;

import vakcinac.io.civil.servant.models.izv.IzvestajOImunizaciji;
import vakcinac.io.core.results.agres.AggregateResult;

public class IzvestajOImunizacijiFactory {
	
	public static IzvestajOImunizaciji create(LocalDate startDate, LocalDate endDate, int numOfZahtev, int numOfIzjava, int numOfSertifikat, AggregateResult aggregatedByDoses, AggregateResult aggregatedByTypes) {
		IzvestajOImunizaciji izvestaj = new IzvestajOImunizaciji();
		izvestaj.setOd(startDate);
		izvestaj.setDo(endDate);
		izvestaj.setIzdato(LocalDate.now());
		
		IzvestajOImunizaciji.StatistikaZahteva statistikaZahteva = new IzvestajOImunizaciji.StatistikaZahteva();
		statistikaZahteva.setPodneto(BigInteger.valueOf(numOfIzjava));
		statistikaZahteva.setPrimljeno(BigInteger.valueOf(numOfZahtev));
		statistikaZahteva.setIzdato(BigInteger.valueOf(numOfSertifikat));
		
		izvestaj.setStatistikaZahteva(statistikaZahteva);
		
		int totalDoses = 0;
		IzvestajOImunizaciji.StatistikaDoza.IzdateDoze izdateDoze = new IzvestajOImunizaciji.StatistikaDoza.IzdateDoze();
		for(AggregateResult.Group group : aggregatedByDoses.getGroup()) {
			IzvestajOImunizaciji.StatistikaDoza.IzdateDoze.Doza doza = new IzvestajOImunizaciji.StatistikaDoza.IzdateDoze.Doza(); 
			doza.setRedniBroj(BigInteger.valueOf(Integer.parseInt(group.getBy())));
			doza.setValue(group.getValue());
			izdateDoze.getDoza().add(doza);

			totalDoses += doza.getValue().intValue();
		}
		
		IzvestajOImunizaciji.StatistikaDoza statistikaDoza = new IzvestajOImunizaciji.StatistikaDoza();
		statistikaDoza.setIzdateDoze(izdateDoze);
		statistikaDoza.setUkupnoIzdatihDoza(BigInteger.valueOf(totalDoses));
		
		izvestaj.setStatistikaDoza(statistikaDoza);
		
		
		IzvestajOImunizaciji.StatistikaProizvodjaca statistikaProizvodjaca = new IzvestajOImunizaciji.StatistikaProizvodjaca();
		for(AggregateResult.Group group : aggregatedByTypes.getGroup()) {
			IzvestajOImunizaciji.StatistikaProizvodjaca.Proizvodjac proizvodjac = new IzvestajOImunizaciji.StatistikaProizvodjaca.Proizvodjac();
			proizvodjac.setNaziv(group.getBy());
			proizvodjac.setBrojDoza(group.getValue());
			statistikaProizvodjaca.getProizvodjac().add(proizvodjac);
		}
		
		izvestaj.setStatistikaProizvodjaca(statistikaProizvodjaca);

		return izvestaj;
	}

}
