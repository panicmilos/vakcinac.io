package vakcinac.io.citizen.validators.domacigradjanin;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.citizen.request.CreateDomaciGradjanin;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType= CreateDomaciGradjanin.class)
public class CreateDomaciGradjaninValidator extends AbstractValidator<CreateDomaciGradjanin> {
	
	@Override
    public void rules() {
		ruleFor(CreateDomaciGradjanin::getIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Ime je obavezno.")
	        .withFieldName("Ime");

		ruleFor(CreateDomaciGradjanin::getPrezime)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Prezime je obavezno.")
	        .withFieldName("Prezime");
		
		ruleFor(CreateDomaciGradjanin::getKorisnickoIme)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Korisnicko ime je obavezno.")
	        .withFieldName("KorisnickoIme");
		
		ruleFor(CreateDomaciGradjanin::getLozinka)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Lozinka je obavezna.")
	        .withFieldName("Lozinka");
	}
}
