package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.civil.servant.models.pot.KreiranjePotvrde;
import vakcinac.io.civil.servant.requests.VaccinateRequest;
import vakcinac.io.civil.servant.service.PotvrdaService;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.validators.ObjectValidator;

@Controller
@RequestMapping(path = "potvrde", produces = { "application/xml" })
public class PotvrdaController extends ControllerBase {

    @Autowired
    private PotvrdaService potvrdaService;

    protected PotvrdaController(ModelMapper mapper, ObjectValidator validator) {
        super(mapper, validator);
    }

    @PostMapping
    public ResponseEntity<KreiranjePotvrde> vaccinated(@RequestBody VaccinateRequest request) throws Exception {
        validate(request);
        
        potvrdaService.create(request.getJmbgOsobe());
        
        return null;
    }

}
