package vakcinac.io.citizen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.core.Constants;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.repository.ExistRepository;
import vakcinac.io.core.repository.jena.JenaRepository;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;
import vakcinac.io.core.utils.parsers.JaxBParser;
import vakcinac.io.core.utils.parsers.JaxBParserFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Random;

@Service
@RequestScope
public class PotvrdaService extends BaseService<PotvrdaOIzvrsenojVakcinaciji> {

    @Autowired
    private GradjaninService gradjaninService;

    protected PotvrdaService(ExistRepository<PotvrdaOIzvrsenojVakcinaciji> baseRepository, JenaRepository jenaRepository) {
        super(baseRepository, jenaRepository);
    }

    @Override
    public PotvrdaOIzvrsenojVakcinaciji create(PotvrdaOIzvrsenojVakcinaciji potvrda) throws Exception {

        String id = getValidPotvrdaId();

        potvrda.setQrKod(String.format("%s/potvrda/%s", Constants.ROOT_URL, id));

        Tgradjanin gradjanin = gradjaninService.findById(potvrda.getPodaciOVakcinisanom().getJmbg());

        fillOutPodaciOVakcinisanom(potvrda, gradjanin);

        return create(id, potvrda);
    }

    public PotvrdaOIzvrsenojVakcinaciji addDoza(String id, String serija) throws XMLDBException {
        PotvrdaOIzvrsenojVakcinaciji potvrda = read(id);

        PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza primljenaDoza = new PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza();
        primljenaDoza.setDatum(LocalDate.now());
        primljenaDoza.setSerija(serija);

        int brDoza = potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().size();
        primljenaDoza.setRedniBroj(new BigInteger(String.valueOf(brDoza + 1)));

        JaxBParser parser = JaxBParserFactory.newInstanceFor(PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji.PodaciODozama.PrimljenaDoza.class, Boolean.FALSE);
        String serializedObj = parser.marshall(primljenaDoza);

        baseRepository.append(id, "//*:podaci-o-dozama", serializedObj);
        potvrda.getPodaciOVakcinaciji().getPodaciODozama().getPrimljenaDoza().add(primljenaDoza);

        return potvrda;
    }

    private void fillOutPodaciOVakcinisanom(PotvrdaOIzvrsenojVakcinaciji potvrda, Tgradjanin tgradjanin) {
        potvrda.getPodaciOVakcinisanom().setPol(tgradjanin.getPol());
        potvrda.getPodaciOVakcinisanom().setIme(tgradjanin.getIme());
        potvrda.getPodaciOVakcinisanom().setPrezime(tgradjanin.getPrezime());
        potvrda.getPodaciOVakcinisanom().setDatumRodjenja(LocalDateUtils.from(tgradjanin.getDatumRodjenja()));
    }

    private String getValidPotvrdaId() {
        String id = "";

        do {
            id =  generatePotvrdaId();
        } while (baseRepository.retrieve(id) != null);

        return id;
    }

    private String generatePotvrdaId() {
        return String.format("%s-%s", generateRandomNumericString(6), generateRandomNumericString(6));
    }

    private String generateRandomNumericString(int length) {
            String SALTCHARS = "01234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < length) {
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            return saltStr;
    }
}
