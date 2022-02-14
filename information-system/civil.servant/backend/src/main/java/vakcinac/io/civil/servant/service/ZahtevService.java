package vakcinac.io.civil.servant.service;

import org.springframework.stereotype.Service;

import vakcinac.io.civil.servant.models.zah.ZahtevZaIzdavanjeZelenogSertifikata;
import vakcinac.io.civil.servant.repository.ZahtevRepository;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.services.BaseService;
import vakcinac.io.core.utils.LocalDateUtils;

@Service
public class ZahtevService extends BaseService<ZahtevZaIzdavanjeZelenogSertifikata> {

	private GradjaninService gradjaninService;
	
	public ZahtevService(GradjaninService gradjaninService, ZahtevRepository zahtevRepository) {
		super(zahtevRepository);
		
		this.gradjaninService = gradjaninService;
	}

	@Override
	public ZahtevZaIzdavanjeZelenogSertifikata create(ZahtevZaIzdavanjeZelenogSertifikata zahtev) throws Exception {
		
		String jmbg = zahtev.getPodnosilacZahteva().getJmbg();
		Tgradjanin gradjanin = gradjaninService.read(jmbg);
		
		fillRestOfZahtev(zahtev, gradjanin);
		
		String id = String.format("%s_%d", jmbg, baseRepository.count(jmbg) + 1);
		
		return create(id, zahtev);
	}
	
	private void fillRestOfZahtev(ZahtevZaIzdavanjeZelenogSertifikata zahtev, Tgradjanin gradjanin) {
		zahtev.getPodnosilacZahteva().setIme(gradjanin.getIme());
		zahtev.getPodnosilacZahteva().setPrezime(gradjanin.getPrezime());
		zahtev.getPodnosilacZahteva().setPol(gradjanin.getPol());
		zahtev.getPodnosilacZahteva().setDatumRodjenja(LocalDateUtils.from(gradjanin.getDatumRodjenja()));
		
		// TODO: DODATI PASOS
	}

}
