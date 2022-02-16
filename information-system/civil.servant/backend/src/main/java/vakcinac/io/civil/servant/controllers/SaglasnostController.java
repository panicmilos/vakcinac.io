package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.civil.servant.factories.SaglasnostZaSprovodjenjePreporuceneImunizacijeFactory;
import vakcinac.io.civil.servant.models.sag.AzurirajSaglasnost;
import vakcinac.io.civil.servant.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.civil.servant.service.SaglasnostService;
import vakcinac.io.civil.servant.validators.CivilServantValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSaglasnostRequest;
import vakcinac.io.core.requests.UpdateSaglasnostRequest;

@Controller
@RequestMapping("/saglasnosti")
public class SaglasnostController extends ControllerBase {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public SaglasnostController(ModelMapper mapper, CivilServantValidator validator) {
        super(mapper, validator);
    }

    @PostMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> apply(@RequestBody CreateSaglasnostRequest createSaglasnostRequest) throws Exception {
        validate(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = SaglasnostZaSprovodjenjePreporuceneImunizacijeFactory.create(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije createdSaglasnost = saglasnostService.create(saglasnost);

        return ResponseEntity.ok(createdSaglasnost);
    }

    @PutMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> update(@RequestBody UpdateSaglasnostRequest updateSaglasnostRequest) throws Exception {
        validate(updateSaglasnostRequest);

        AzurirajSaglasnost noviPodaci = mapper.map(updateSaglasnostRequest, AzurirajSaglasnost.class);
        SaglasnostZaSprovodjenjePreporuceneImunizacije updatedSaglasnost = saglasnostService.update(noviPodaci);

        return ResponseEntity.ok(updatedSaglasnost);
    }
}
