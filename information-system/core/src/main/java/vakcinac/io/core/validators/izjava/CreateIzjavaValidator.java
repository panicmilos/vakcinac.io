package vakcinac.io.core.validators.izjava;

import java.util.List;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.predicate.ComparablePredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import vakcinac.io.core.requests.CreateIzjavaRequest;
import vakcinac.io.core.utils.RegexPatterns;
import vakcinac.io.core.validators.RegisteredValidator;

@RegisteredValidator(forType = CreateIzjavaRequest.class)
public class CreateIzjavaValidator extends AbstractValidator<CreateIzjavaRequest> {

	@Override
	public void rules() {
	
        ruleFor(CreateIzjavaRequest::getDrzavljanstvo)
	        .must(ComparablePredicate.betweenInclusive(0, 2))
	        .withMessage("Drzavljanstvo je obavezano.")
	        .withFieldName("Drzavljanstvo");
        
		ruleFor(CreateIzjavaRequest::getPodnosilac)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Podnosilac je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.JMBG_PATTERN))
	        .withMessage("Podnosilac nije u dobrom formatu.")
	        .withFieldName("Podnosilac");
		
		ruleFor(CreateIzjavaRequest::getBrojMobilnogTelefona)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Broj mobilnog telefona je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.BR_MOB_PATTERN))
	        .withMessage("Broj mobilnog telefona nije u dobrom formatu.")
	        .withFieldName("BrojMobilnogTelefona");
	
		ruleFor(CreateIzjavaRequest::getBrojFiksnogTelefona)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Broj fiksnog telefona je obavezan.")
	        .must(StringPredicate.stringMatches(RegexPatterns.BR_FIKS_PATTERN))
	        .withMessage("Broj fiksnog telefona nije u dobrom formatu.")
	        .withFieldName("BrojFiksnogTelefona");

		ruleFor(CreateIzjavaRequest::getOpstina)
	        .must(StringPredicate.stringEmptyOrNull().negate())
	        .withMessage("Opstina je obavezna.")
	        .withFieldName("Opstina");
		
        ruleFor(CreateIzjavaRequest::getProizvodjac)
	    	.must(CreateIzjavaValidator::isProizvodjacValid)
	        .withMessage("Neki od proizvodjača nisu validni.")
	        .withFieldName("Proizvodjac");
	}
	
	private static boolean isProizvodjacValid(List<Integer> proizvodjaci) {
		if (proizvodjaci == null || proizvodjaci.size() == 0) {
			return false;
		}
		
		for (int proizvodjac : proizvodjaci) {
			if (proizvodjac < 0 || proizvodjac > 5) {
				return false;
			}
		}
		
		return true;
	}

}
