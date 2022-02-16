package vakcinac.io.civil.servant.validators.saglasnost;

import vakcinac.io.core.requests.CreateSaglasnostRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.saglasnost.CreateSaglasnostValidator;

@RegisteredValidator(forType = CreateSaglasnostRequest.class)
public class CreateSaglasnostRequestValidator extends CreateSaglasnostValidator {
}
