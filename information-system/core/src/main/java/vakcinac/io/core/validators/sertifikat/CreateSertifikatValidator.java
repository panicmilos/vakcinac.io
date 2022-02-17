package vakcinac.io.core.validators.sertifikat;

import java.math.BigInteger;
import java.util.Collection;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.core.requests.helpers.SertifikatTest;
import vakcinac.io.core.utils.StringUtils;

public class CreateSertifikatValidator extends AbstractValidator<CreateSertifikatRequest> {
	
	@Override
    public void rules() {

        ruleFor(CreateSertifikatRequest::getGradjaninId)
            .must(StringPredicate.stringEmptyOrNull().negate())
            .withMessage("Id građanina je obavezan.")
            .withFieldName("GradjaninId");
        
        ruleFor(CreateSertifikatRequest::getZahtev)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Zahtev je obavezan.")
	        .withFieldName("Zahtev");

        ruleFor(CreateSertifikatRequest::getCitizenIdentification)
            .must(ComparablePredicate.betweenInclusive(0, 2))
            .withMessage("Vrsta identifikacije je obavezna.")
            .withFieldName("VrstaIdentifikacije");

        ruleFor(CreateSertifikatRequest::getBrojTestova)
            .must(CreateSertifikatValidator::isBrojTestovaValid)
            .withMessage("Broj testova je obavezan.")
            .withFieldName("BrojTestova");

        ruleFor(CreateSertifikatRequest::getTestovi)
        	.must(CreateSertifikatValidator::areTestsValid)
            .withMessage("Neki od testova nisu validni.")
            .withFieldName("Testovi");
    }
	
	private static boolean isBrojTestovaValid(BigInteger brojTestova) {
		if (brojTestova == null) {
			return false;
		}
		
		return !(brojTestova.compareTo(BigInteger.ZERO) < 0);
	}
	
	private static boolean areTestsValid(Collection<SertifikatTest> testovi) {	
		int iter = 0;
		
		for(SertifikatTest test: testovi) {
			iter++;
			int numOfFieldsEmpty = 0;
			
			if (StringUtils.nullOrEmpty(test.getIme())) {
				return false;
			}
			if (test.getBroj() == null || test.getBroj().compareTo(new BigInteger(Integer.toString(iter))) != 0) {
				return false;
			}
					
			if (StringUtils.nullOrEmpty(test.getVrstaUzorka())) {
				numOfFieldsEmpty++;
			}
			if (StringUtils.nullOrEmpty(test.getProizvodjac())) {
				numOfFieldsEmpty++;
			}
			if (test.getDatumUzorka() == null) {
				numOfFieldsEmpty++;
			}
			if (test.getDatumIzdavanja() == null) {
				numOfFieldsEmpty++;
			}
			if (StringUtils.nullOrEmpty(test.getRezultat())) {
				numOfFieldsEmpty++;
			}
			if (StringUtils.nullOrEmpty(test.getLabaratorija())) {
				numOfFieldsEmpty++;
			}
					
			if (numOfFieldsEmpty > 0 && numOfFieldsEmpty < 6) {
				return false;
			}		
		}
		
		return true;
	}
}
