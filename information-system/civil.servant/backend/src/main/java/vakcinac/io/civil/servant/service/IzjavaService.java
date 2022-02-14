package vakcinac.io.civil.servant.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.xmldb.api.base.XMLDBException;

import vakcinac.io.civil.servant.models.izj.IzjavaInteresovanjaZaVakcinisanje;
import vakcinac.io.civil.servant.repository.IzjavaRepository;
import vakcinac.io.core.models.os.Tgradjanin;
import vakcinac.io.core.services.BaseService;

@Service
@RequestScope
public class IzjavaService extends BaseService<IzjavaInteresovanjaZaVakcinisanje> {

	private GradjaninService gradjaninService;
	
	public IzjavaService(IzjavaRepository izjavaRepository, GradjaninService gradjaninService) {
		super(izjavaRepository);
		
		this.gradjaninService = gradjaninService;
	}

	@Override
	public IzjavaInteresovanjaZaVakcinisanje create(IzjavaInteresovanjaZaVakcinisanje izjava) throws XMLDBException, IOException {

		String jmbg = izjava.getPodnosilacIzjave().getPodnosilac().getJmbg();
		Tgradjanin gradjanin = gradjaninService.read(jmbg);
		
		fillRestOfIzjava(izjava, gradjanin);
		
		String id = String.format("%s_%d", jmbg, baseRepository.count(jmbg) + 1);
		
		return create(id, izjava);
	}
	
	private void fillRestOfIzjava(IzjavaInteresovanjaZaVakcinisanje izjava, Tgradjanin gradjanin) {
		izjava.getPodnosilacIzjave().getPodnosilac().setIme(gradjanin.getIme());
		izjava.getPodnosilacIzjave().getPodnosilac().setPrezime(gradjanin.getPrezime());
		izjava.getPodnosilacIzjave().getKontakt().setAdresaElektronskePoste(gradjanin.getEmail());
	}

}
