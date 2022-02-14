package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.repository.SaglasnostRepository;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.models.os.Tkontakt;
import vakcinac.io.core.services.BaseService;

@Service
public class SaglasnostService extends BaseService<SaglasnostZaSprovodjenjePreporuceneImunizacije> {

    private GradjaninService gradjaninService;

    public SaglasnostService(GradjaninService gradjaninService, SaglasnostRepository saglasnostRepository) {
        super(saglasnostRepository);

        this.gradjaninService = gradjaninService;
    }

    @Override
    public SaglasnostZaSprovodjenjePreporuceneImunizacije create(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost) throws Exception {

        String gradjaninId = "";

        if (saglasnost.getPacijent().getDrzavljanstvo().getDomace() != null) {
            gradjaninId = saglasnost.getPacijent().getDrzavljanstvo().getDomace().getJmbg();
        } else {
            gradjaninId = saglasnost.getPacijent().getDrzavljanstvo().getStrano().getBrojPasosaEbs();
        }

        Tgradjanin gradjanin = gradjaninService.read(gradjaninId);

        fillOutPacijent(saglasnost, gradjanin);

        String id = String.format("%s_%d", gradjaninId, baseRepository.count(gradjaninId) + 1);

        return create(id, saglasnost);
    }

    private void fillOutPacijent(SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost, Tgradjanin gradjanin) {
        saglasnost.getPacijent().setKontakt(new Tkontakt());
        saglasnost.getPacijent().getKontakt().setAdresaElektronskePoste(gradjanin.getEmail());
        saglasnost.getPacijent().getKontakt().setBrojMobilnogTelefona("fdafa");
        saglasnost.getPacijent().getKontakt().setBrojFiksnogTelefona("fdafa");
        saglasnost.getPacijent().getLicneInformacije().setImeRoditelja(gradjanin.getImeRoditelja());
        saglasnost.getPacijent().getLicneInformacije().setIme(gradjanin.getIme());
        saglasnost.getPacijent().getLicneInformacije().setPrezime(gradjanin.getPrezime());
        saglasnost.getPacijent().getLicneInformacije().setPol(gradjanin.getPol());
    }
}
