package vakcinac.io.civil.servant.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vakcinac.io.civil.servant.models.pot.DodajDozu;
import vakcinac.io.civil.servant.models.pot.KreiranjePotvrde;
import vakcinac.io.civil.servant.service.PotvrdaService;
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


    @PostMapping(path = "dodaj-dozu")
    public ResponseEntity<DodajDozu> addDoza(@RequestBody AddDozaRequest request) throws Exception {
        validate(request);

        DodajDozu dodajDozu = mapper.map(request, DodajDozu.class);

        DodajDozu doza = potvrdaService.dodajDozu(dodajDozu);
        return ResponseEntity.ok(doza);
    }

    @PostMapping
    public ResponseEntity<KreiranjePotvrde> create(@RequestBody CreatePotvrdaRequest request) throws Exception {
        validate(request);

        KreiranjePotvrde kreiranjePotvrde = mapper.map(request, KreiranjePotvrde.class);

        KreiranjePotvrde doza = potvrdaService.create(kreiranjePotvrde);
        return ResponseEntity.ok(doza);
    }

}
