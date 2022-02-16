package vakcinac.io.citizen.validators.sertifikat;

import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.sertifikat.CreateSertifikatValidator;

@RegisteredValidator(forType = CreateSertifikatRequest.class)
public class CreateSertifikatRequestValidator extends CreateSertifikatValidator {
}
