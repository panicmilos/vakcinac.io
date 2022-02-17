package vakcinac.io.citizen.validators.domacigradjanin;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.citizen.requests.CreateDomaciGradjaninRequest;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.utils.StringUtils;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateDomaciGradjaninRequest.class)
public class CreateDomaciGradjaninValidator extends AbstractValidator<CreateDomaciGradjaninRequest> {
	
	@Override
    public void rules() {
		ruleFor(CreateDomaciGradjaninRequest::getIme)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateDomaciGradjaninRequest::getPrezime)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateDomaciGradjaninRequest::getKorisnickoIme)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateDomaciGradjaninRequest::getLozinka)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
		
		ruleFor(CreateDomaciGradjaninRequest::getDatumRodjenja)
	        .must(DateUtils::isValidDateOfBirth)
	        .withMessage("Datum rođenja nije u dobrom formatu.")
	        .withFieldName("DatumRodjenja");
		
		ruleFor(CreateDomaciGradjaninRequest::getPol)
	        .must(ComparablePredicate.betweenInclusive(0, 1))
	        .withMessage("Pol je obavezan.")
	        .withFieldName("Pol");
		
		ruleFor(CreateDomaciGradjaninRequest::getEmail)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Email je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.EMAIL_PATTERN))
	        .withMessage("Email nije u dobrom formatu.")
	        .withFieldName("Email");
		
		ruleFor(CreateDomaciGradjaninRequest::getImeRoditelja)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Ime roditelja je obavezno.")
	        .withFieldName("ImeRoditelja");
		
		ruleFor(CreateDomaciGradjaninRequest::getMestoRodjenja)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto rodjenja je obavezno.")
	        .withFieldName("MestoRodjenja");
			
		ruleFor(CreateDomaciGradjaninRequest::getAdresa)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Adresa je obavezna.")
	        .withFieldName("Adresa");
		
		ruleFor(CreateDomaciGradjaninRequest::getMesto)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Mesto je obavezno.")
	        .withFieldName("Mesto");
		
		ruleFor(CreateDomaciGradjaninRequest::getOpstina)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Opstina je obavezna.")
	        .withFieldName("Opstina");
		
		ruleFor(CreateDomaciGradjaninRequest::getJmbg)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Jmbg je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Jmbg nije u dobrom formatu.")
	        .withFieldName("Jmbg");
		
		ruleFor(CreateDomaciGradjaninRequest::getBrojMobilnogTelefona)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Broj mobilnog telefona je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.BR_MOB_PATTERN))
	        .withMessage("Broj mobilnog telefona nije u dobrom formatu.")
	        .withFieldName("BrojMobilnogTelefona");
		
		ruleFor(CreateDomaciGradjaninRequest::getBrojFiksnogTelefona)
	        .must(StringUtils::notNullOrEmpty)
	        .withMessage("Broj fiksnog telefona je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.BR_FIKS_PATTERN))
	        .withMessage("Broj fiksnog telefona nije u dobrom formatu.")
	        .withFieldName("BrojFiksnogTelefona");
	}
}
