package vakcinac.io.citizen.factories;

import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.models.pot.TpodaciOVakcinisanom;
import vakcinac.io.core.requests.CreatePotvrdaRequest;

import java.math.BigInteger;
import java.time.LocalDate;

public class PotvrdaOIzvrsenojVakcinacijiFactory {

    public static PotvrdaOIzvrsenojVakcinaciji create(CreatePotvrdaRequest request) {
        PotvrdaOIzvrsenojVakcinaciji potvrda = new PotvrdaOIzvrsenojVakcinaciji();

        potvrda.setDatumIzdavanja(LocalDate.now());
        potvrda.setBrojPrimljenihDoza(new BigInteger("1"));
        potvrda.setPodaciOVakcinisanom(new TpodaciOVakcinisanom());
        potvrda.getPodaciOVakcinisanom().setJmbg(request.getJmbgOsobe());

        potvrda.setPodaciOVakcinaciji(new PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji());
        potvrda.getPodaciOVakcinaciji().setNazivVakcine(request.getNazivVakcine());
        potvrda.getPodaciOVakcinaciji().setZdravstvenaUstanova("/");
        potvrda.getPodaciOVakcinaciji().setPodaciODozama(new PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama());

        PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza primljenaDoza = new PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza();
        primljenaDoza.setDatum(LocalDate.now());
        primljenaDoza.setRedniBroj(new BigInteger("1"));
        primljenaDoza.setSerija(request.getSerija());

        potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().add(primljenaDoza);

        return potvrda;
    }
}
