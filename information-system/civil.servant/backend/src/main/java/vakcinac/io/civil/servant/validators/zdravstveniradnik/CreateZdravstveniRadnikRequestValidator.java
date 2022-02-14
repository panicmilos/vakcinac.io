package vakcinac.io.civil.servant.validators.zdravstveniradnik;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.civil.servant.requests.CreateZdravstveniRadnikRequest;
import vakcinac.io.core.utils.DateUtils;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateZdravstveniRadnikRequest.class)
public class CreateZdravstveniRadnikRequestValidator extends AbstractValidator<CreateZdravstveniRadnikRequest> {
	
	@Override
    public void rules() {
		ruleFor(CreateZdravstveniRadnikRequest::getIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateZdravstveniRadnikRequest::getPrezime)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateZdravstveniRadnikRequest::getKorisnickoIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateZdravstveniRadnikRequest::getLozinka)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
		
		ruleFor(CreateZdravstveniRadnikRequest::getDatumRodjenja)
	        .must(DateUtils::isValidDateOfBirth)
	        .withMessage("Datum rođenja nije u dobrom formatu.")
	        .withFieldName("DatumRodjenja");
		
		ruleFor(CreateZdravstveniRadnikRequest::getPol)
	        .must(ComparablePredicate.betweenInclusive(0, 1))
	        .withMessage("Pol je obavezan.")
	        .withFieldName("Pol");
		
		ruleFor(CreateZdravstveniRadnikRequest::getEmail)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Email je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.EMAIL_PATTERN))
	        .withMessage("Email nije u dobrom formatu.")
	        .withFieldName("Email");
		
		ruleFor(CreateZdravstveniRadnikRequest::getJmbg)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Jmbg je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Jmbg nije u dobrom formatu.")
	        .withFieldName("Jmbg");
		
		ruleFor(CreateZdravstveniRadnikRequest::getFaksimil)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Faksimil je obavezan.")
	        .withFieldName("Faksimil");
		
		ruleFor(CreateZdravstveniRadnikRequest::getBrojTelefona)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Broj telefona je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.BR_MOB_PATTERN))
	        .withMessage("Broj telefona nije u dobrom formatu.")
	        .withFieldName("BrojTelefona");	
	}
}
