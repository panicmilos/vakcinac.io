package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.citizen.factories.PotvrdaOIzvrsenojVakcinacijiFactory;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.service.PotvrdaService;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.AddDozaRequest;
import vakcinac.io.core.requests.CreatePotvrdaRequest;
import vakcinac.io.core.validators.ObjectValidator;

@Controller
@RequestMapping("potvrde")
public class PotvrdaController extends ControllerBase {

    @Autowired
    private PotvrdaService potvrdaService;

    protected PotvrdaController(ModelMapper mapper, ObjectValidator validator) {
        super(mapper, validator);
    }

    @PostMapping
    public ResponseEntity<PotvrdaOIzvrsenojVakcinaciji> apply(@RequestBody CreatePotvrdaRequest request) throws Exception {

        PotvrdaOIzvrsenojVakcinaciji potvrda = PotvrdaOIzvrsenojVakcinacijiFactory.create(request);

        PotvrdaOIzvrsenojVakcinaciji createdPotvrda = potvrdaService.create(potvrda);

        return ResponseEntity.ok(createdPotvrda);
    }

    @PostMapping(path = "dodaj-dozu")
    public ResponseEntity<PotvrdaOIzvrsenojVakcinaciji> addDoza(@RequestBody AddDozaRequest request) throws Exception {

        PotvrdaOIzvrsenojVakcinaciji updatedPotvrda = potvrdaService.addDoza(request.getPotvrdaId(), request.getSerija());

        return ResponseEntity.ok(updatedPotvrda);
    }
}
