package vakcinac.io.citizen.validators.stranigradjanin;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.citizen.requests.CreateStraniGradjaninRequest;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateStraniGradjaninRequest.class)
public class CreateStraniGradjaninValidator extends AbstractValidator<CreateStraniGradjaninRequest> {
	@Override
    public void rules() {
		ruleFor(CreateStraniGradjaninRequest::getIme)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateStraniGradjaninRequest::getPrezime)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateStraniGradjaninRequest::getKorisnickoIme)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateStraniGradjaninRequest::getLozinka)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
		
		ruleFor(CreateStraniGradjaninRequest::getDatumRodjenja)
	        .must(DateUtils::isValidDateOfBirth)
	        .withMessage("Datum rođenja nije u dobrom formatu.")
	        .withFieldName("DatumRodjenja");
		
		ruleFor(CreateStraniGradjaninRequest::getPol)
	        .must(ComparablePredicate.betweenInclusive(0, 1))
	        .withMessage("Pol je obavezan.")
	        .withFieldName("Pol");
		
		ruleFor(CreateStraniGradjaninRequest::getEmail)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Email je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.EMAIL_PATTERN))
	        .withMessage("Email nije u dobrom formatu.")
	        .withFieldName("Email");
		
		ruleFor(CreateStraniGradjaninRequest::getImeRoditelja)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Ime roditelja je obavezno.")
	        .withFieldName("ImeRoditelja");
		
		ruleFor(CreateStraniGradjaninRequest::getMestoRodjenja)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto rodjenja je obavezno.")
	        .withFieldName("MestoRodjenja");
			
		ruleFor(CreateStraniGradjaninRequest::getAdresa)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Adresa je obavezna.")
	        .withFieldName("Adresa");
		
		ruleFor(CreateStraniGradjaninRequest::getMesto)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
		
		ruleFor(CreateStraniGradjaninRequest::getOpstina)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Opstina je obavezna.")
	        .withFieldName("Opstina");
		
		ruleFor(CreateStraniGradjaninRequest::getIdentifikacioniDokument)
	        .must(ComparablePredicate.betweenInclusive(0, 1))
	        .withMessage("Identifikacioni dokument je obavezan.")
	        .withFieldName("IdentifikacioniDokument");
		
		ruleFor(CreateStraniGradjaninRequest::getBrojPasosa)
	        .must(CreateStraniGradjaninValidator::isBrojPasosaValid)
	        .withMessage("Broj pasosa nije u dobrom formatu.")
	        .withFieldName("BrojPasosa");
		
		ruleFor(CreateStraniGradjaninRequest::getEbs)
	        .must(CreateStraniGradjaninValidator::isEbsValid)
	        .withMessage("Ebs nije u dobrom formatu.")
	        .withFieldName("Ebs");
		
		ruleFor(CreateStraniGradjaninRequest::getDrzava)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Drzava je obavezna.")
	        .withFieldName("Drzava");
	}
	
	private static boolean isBrojPasosaValid(String brojPasosa) {
		if (StringUtils.nullOrEmpty(brojPasosa)) {
			return true;
		}
		
		return brojPasosa.matches(RegexPatterns.PASOS_PATTERN);
	}
	
	private static boolean isEbsValid(String ebs) {
		if (StringUtils.nullOrEmpty(ebs)) {
			return true;
		}
		
		return ebs.matches(RegexPatterns.EBS_PATTERN);
	}
}
