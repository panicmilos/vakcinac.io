package vakcinac.io.civil.servant.validators.zahtev;

import vakcinac.io.core.requests.CreateZahtevRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.zahtev.CreateZahtevValidator;

@RegisteredValidator(forType = CreateZahtevRequest.class)
public class CreateZahtevRequestValidator extends CreateZahtevValidator {

}
