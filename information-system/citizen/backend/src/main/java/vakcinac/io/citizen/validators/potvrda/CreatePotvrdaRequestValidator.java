package vakcinac.io.citizen.validators.potvrda;

import vakcinac.io.core.requests.CreatePotvrdaRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.potvrda.CreatePotvrdaValidator;

@RegisteredValidator(forType = CreatePotvrdaRequest.class)
public class CreatePotvrdaRequestValidator extends CreatePotvrdaValidator {
}
