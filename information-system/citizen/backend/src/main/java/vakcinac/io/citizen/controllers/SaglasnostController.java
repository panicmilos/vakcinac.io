package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.service.SaglasnostService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSaglasnostRequest;

@Controller
@RequestMapping("/saglasnosti")
public class SaglasnostController extends ControllerBase {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public SaglasnostController(ModelMapper mapper, CitizenValidator validator) {
        super(mapper, validator);
    }

    @PostMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> apply(@RequestBody CreateSaglasnostRequest createSaglasnostRequest) throws Exception {

        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = mapper.map(createSaglasnostRequest, SaglasnostZaSprovodjenjePreporuceneImunizacije.class);

        SaglasnostZaSprovodjenjePreporuceneImunizacije createdSaglasnost = saglasnostService.create(saglasnost);

        return ResponseEntity.ok(createdSaglasnost);
    }
}
