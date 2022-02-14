package vakcinac.io.citizen.validators.domacigradjanin;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.citizen.requests.CreateDomaciGradjaninRequest;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateDomaciGradjaninRequest.class)
public class CreateDomaciGradjaninValidator extends AbstractValidator<CreateDomaciGradjaninRequest> {
	
	@Override
    public void rules() {
		ruleFor(CreateDomaciGradjaninRequest::getIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateDomaciGradjaninRequest::getPrezime)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateDomaciGradjaninRequest::getKorisnickoIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateDomaciGradjaninRequest::getLozinka)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
	}
}
