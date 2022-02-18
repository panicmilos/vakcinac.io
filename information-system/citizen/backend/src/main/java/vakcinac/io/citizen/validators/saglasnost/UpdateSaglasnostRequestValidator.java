package vakcinac.io.citizen.validators.saglasnost;

import vakcinac.io.core.requests.UpdateSaglasnostRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.saglasnost.UpdateSaglasnostValidator;

@RegisteredValidator(forType = UpdateSaglasnostRequest.class)
public class UpdateSaglasnostRequestValidator extends UpdateSaglasnostValidator {
}
