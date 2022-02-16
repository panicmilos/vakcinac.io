package vakcinac.io.civil.servant.validators.sertifikat;

import vakcinac.io.core.requests.CreateSertifikatRequest;
import vakcinac.io.core.validators.RegisteredValidator;
import vakcinac.io.core.validators.sertifikat.CreateSertifikatValidator;

@RegisteredValidator(forType = CreateSertifikatRequest.class)
public class CreateSertifikatRequestValidator extends CreateSertifikatValidator {
}
