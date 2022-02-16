package vakcinac.io.citizen.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import vakcinac.io.citizen.factories.PotvrdaOIzvrsenojVakcinacijiFactory;
import vakcinac.io.citizen.models.pot.PotvrdaOIzvrsenojVakcinaciji;
import vakcinac.io.citizen.service.PotvrdaService;
import vakcinac.io.core.controllers.ControllerBase;
import vakcinac.io.core.models.os.InformacijeOPrimljenimDozamaIzPotvrde;
import vakcinac.io.core.models.os.PrimljenaDozaIzPotvrde;
import vakcinac.io.core.requests.AddDozaRequest;
import vakcinac.io.core.requests.CreatePotvrdaRequest;
import vakcinac.io.core.results.agres.AggregateResult;
import vakcinac.io.core.validators.ObjectValidator;

@Controller
@RequestMapping("potvrde")
public class PotvrdaController extends ControllerBase {

    @Autowired
    private PotvrdaService potvrdaService;

    protected PotvrdaController(ModelMapper mapper, ObjectValidator validator) {
        super(mapper, validator);
    }

    @GetMapping("aggregate/doses")
    public ResponseEntity<AggregateResult> aggregateByDoses(@RequestParam(name="startDate") String startDateS, @RequestParam(name="endDate") String endDateS) throws Exception {

		LocalDate startDate = LocalDate.parse(startDateS);
		LocalDate endDate = LocalDate.parse(endDateS);

        return ResponseEntity.ok(potvrdaService.aggregateByDose(startDate, endDate));
    }
    
    @GetMapping("aggregate/types")
    public ResponseEntity<AggregateResult> aggregateByTypes(@RequestParam(name="startDate") String startDateS, @RequestParam(name="endDate") String endDateS) throws Exception {

		LocalDate startDate = LocalDate.parse(startDateS);
		LocalDate endDate = LocalDate.parse(endDateS);

        return ResponseEntity.ok(potvrdaService.aggregateByTypes(startDate, endDate));
    }
    
    @PostMapping
    public ResponseEntity<PotvrdaOIzvrsenojVakcinaciji> apply(@RequestBody CreatePotvrdaRequest request) throws Exception {
        validate(request);

        PotvrdaOIzvrsenojVakcinaciji potvrda = PotvrdaOIzvrsenojVakcinacijiFactory.create(request);

        PotvrdaOIzvrsenojVakcinaciji createdPotvrda = potvrdaService.create(potvrda);

        return ResponseEntity.ok(createdPotvrda);
    }

    @PostMapping(path = "dodaj-dozu")
    public ResponseEntity<PotvrdaOIzvrsenojVakcinaciji> addDoza(@RequestBody AddDozaRequest request) throws Exception {
        validate(request);

        PotvrdaOIzvrsenojVakcinaciji updatedPotvrda = potvrdaService.addDoza(request.getPotvrdaId(), request.getSerija());

        return ResponseEntity.ok(updatedPotvrda);
    }

    @GetMapping(path = "gradjanin/{gradjaninId}/doze")
    public ResponseEntity<InformacijeOPrimljenimDozamaIzPotvrde> primljeneDoze(@PathVariable String gradjaninId) throws Exception {

        PotvrdaOIzvrsenojVakcinaciji.PodaciOVakcinaciji info = potvrdaService.readVakcinePotvrdePoGradjanu(gradjaninId);

        List<PrimljenaDozaIzPotvrde> doze = info.getPodaciODozama().getPrimljenaDoza().stream().map(doza ->
            new PrimljenaDozaIzPotvrde() {
            {
                setDatum(doza.getDatum());
                setRedniBroj(doza.getRedniBroj());
                setSerija(doza.getSerija());
            }
        }).collect(Collectors.toList());

        InformacijeOPrimljenimDozamaIzPotvrde informacijeOPrimljenimDozamaIzPotvrde = new InformacijeOPrimljenimDozamaIzPotvrde();
        informacijeOPrimljenimDozamaIzPotvrde.setNazivVakcine(info.getNazivVakcine());
        informacijeOPrimljenimDozamaIzPotvrde.setZdravstvenaUstanova(info.getZdravstvenaUstanova());
        informacijeOPrimljenimDozamaIzPotvrde.setPrimljenaDozaIzPotvrde(doze);

        System.out.println(doze.size());

        return ResponseEntity.ok(informacijeOPrimljenimDozamaIzPotvrde);
    }
}
