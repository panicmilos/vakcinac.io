package vakcinac.io.citizen.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vakcinac.io.citizen.models.sag.SaglasnostZaSprovodjenjePreporuceneImunizacije;
import vakcinac.io.citizen.service.SaglasnostService;
import vakcinac.io.citizen.validators.CitizenValidator;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.requests.CreateSaglasnostRequest;

@Controller
@CrossOrigin("*")
@RequestMapping("/saglasnosti")
public class SaglasnostController extends ControllerBase {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public SaglasnostController(ModelMapper mapper, CitizenValidator validator) {
        super(mapper, validator);
    }
    
    @PreAuthorize("hasAnyRole('DomaciGradjanin', 'StraniGradjanin')")
    @PostMapping
    public ResponseEntity<SaglasnostZaSprovodjenjePreporuceneImunizacije> create(@RequestBody CreateSaglasnostRequest createSaglasnostRequest) throws Exception {
        validate(createSaglasnostRequest);

        SaglasnostZaSprovodjenjePreporuceneImunizacije saglasnost = mapper.map(createSaglasnostRequest, SaglasnostZaSprovodjenjePreporuceneImunizacije.class);

        SaglasnostZaSprovodjenjePreporuceneImunizacije createdSaglasnost = saglasnostService.create(saglasnost);

        return ResponseEntity.ok(createdSaglasnost);
    }
}
