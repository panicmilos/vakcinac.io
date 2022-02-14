package vakcinac.io.citizen.validators.stranigradjanin;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.citizen.requests.CreateStraniGradjaninRequest;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateStraniGradjaninRequest.class)
public class CreateStraniGradjaninValidator extends AbstractValidator<CreateStraniGradjaninRequest> {
	@Override
    public void rules() {
		ruleFor(CreateStraniGradjaninRequest::getIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateStraniGradjaninRequest::getPrezime)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateStraniGradjaninRequest::getKorisnickoIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateStraniGradjaninRequest::getLozinka)
	        .must(StringPredicate.stringEmptyOrNull().negate())
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
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Email je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.EMAIL_PATTERN))
	        .withMessage("Email nije u dobrom formatu.")
	        .withFieldName("Email");
		
		ruleFor(CreateStraniGradjaninRequest::getImeRoditelja)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime roditelja je obavezno.")
	        .withFieldName("ImeRoditelja");
		
		ruleFor(CreateStraniGradjaninRequest::getMestoRodjenja)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Mesto rodjenja je obavezno.")
	        .withFieldName("MestoRodjenja");
			
		ruleFor(CreateStraniGradjaninRequest::getAdresa)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Adresa je obavezna.")
	        .withFieldName("Adresa");
		
		ruleFor(CreateStraniGradjaninRequest::getMesto)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
		
		ruleFor(CreateStraniGradjaninRequest::getOpstina)
	        .must(StringPredicate.stringEmptyOrNull().negate())
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
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Drzava je obavezna.")
	        .withFieldName("Drzava");
	}
	
	private static boolean isBrojPasosaValid(String brojPasosa) {
		if (brojPasosa == null || brojPasosa.isEmpty()) {
			return true;
		}
		
		return brojPasosa.matches(RegexPatterns.PASOS_PATTERN);
	}
	
	private static boolean isEbsValid(String ebs) {
		if (ebs == null || ebs.isEmpty()) {
			return true;
		}
		
		return ebs.matches(RegexPatterns.EBS_PATTERN);
	}
}
