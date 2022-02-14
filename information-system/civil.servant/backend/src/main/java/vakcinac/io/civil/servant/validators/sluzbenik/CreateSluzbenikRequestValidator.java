package vakcinac.io.civil.servant.validators.sluzbenik;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.civil.servant.requests.CreateSluzbenikRequest;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = CreateSluzbenikRequest.class)
public class CreateSluzbenikRequestValidator extends AbstractValidator<CreateSluzbenikRequest> {
	
	@Override
    public void rules() {
		ruleFor(CreateSluzbenikRequest::getIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateSluzbenikRequest::getPrezime)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateSluzbenikRequest::getKorisnickoIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateSluzbenikRequest::getLozinka)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
		
		ruleFor(CreateSluzbenikRequest::getDatumRodjenja)
	        .must(DateUtils::isValidDateOfBirth)
	        .withMessage("Datum rođenja nije u dobrom formatu.")
	        .withFieldName("DatumRodjenja");
		
		ruleFor(CreateSluzbenikRequest::getPol)
	        .must(ComparablePredicate.betweenInclusive(0, 1))
	        .withMessage("Pol je obavezan.")
	        .withFieldName("Pol");
		
		ruleFor(CreateSluzbenikRequest::getEmail)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Email je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.EMAIL_PATTERN))
	        .withMessage("Email nije u dobrom formatu.")
	        .withFieldName("Email");
		
		ruleFor(CreateSluzbenikRequest::getJmbg)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Jmbg je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Jmbg nije u dobrom formatu.")
	        .withFieldName("Jmbg");
	}
}
