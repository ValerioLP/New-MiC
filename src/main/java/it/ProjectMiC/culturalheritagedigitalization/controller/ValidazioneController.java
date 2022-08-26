package it.ProjectMiC.culturalheritagedigitalization.controller;

import it.ProjectMiC.culturalheritagedigitalization.model.APIResponse;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.service.ValidazioneService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ValidazioneController {

    private final ValidazioneService validazioneService;

    public ValidazioneController(ValidazioneService validazioneService) {
        this.validazioneService = validazioneService;
    }

    @GetMapping("/readvalidazione")
    public ResponseEntity<?> readValidazioneById(@RequestParam(required = false) String id_validazione) {
        if(id_validazione == null)
            return new ResponseEntity<>(validazioneService.findAll(), HttpStatus.OK);
        Validazione validazione = validazioneService.findById(id_validazione);
        if(validazione == null)
            return new ResponseEntity<>("Non e' stata trovata nessuna validazione con id: " + id_validazione, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(validazione, HttpStatus.OK);
    }

    @GetMapping("/readvalidazione/{field}")
    public APIResponse<List<Validazione>> getWithSorting(@PathVariable String field) {
        List<Validazione> validazione = validazioneService.findValidazioneBySorting(field);
        return new APIResponse<>(validazione.size(), validazione);
    }

    @GetMapping("/readvalidazione/pagination/{offset}/{pageSize}\"")
    public APIResponse<Page<Validazione>> getWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Validazione> validazione = validazioneService.findWithPagination(offset, pageSize);
        return new APIResponse<>(validazione.getSize(), validazione);
    }

    @PostMapping("/createvalidazione")
    public ResponseEntity<?> createValidazione(@RequestBody Validazione validazione) {
        String checkDescrizione = validazione.getDescrizione();
        String checkEspressione = validazione.getEspressione();

        if(checkDescrizione == null || checkDescrizione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo descrizione", HttpStatus.NOT_ACCEPTABLE);
        if(checkEspressione == null || checkEspressione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo espressione", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(validazioneService.saveValidazione(validazione), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletevalidazione")
    public ResponseEntity<?> deleteValidazione(@RequestParam String id) {
        if(id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire il parametro id", HttpStatus.NOT_ACCEPTABLE);
        Optional<Validazione> validazione = Optional.ofNullable(validazioneService.deleteValidazione(id));
        if(!validazione.isPresent())
            return new ResponseEntity<>("Non e' stata trovata nessuna validazione con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(validazione);
    }

    @PutMapping("/updatevalidazione")
    public ResponseEntity<?> updateValidazione(@RequestBody Validazione validazione) {
        String id = validazione.getId_validazione();
        if(id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire nel body il campo id", HttpStatus.NOT_ACCEPTABLE);
        Optional<Validazione> validazioneOpt = Optional.ofNullable(validazioneService.updateValidazione(validazione));
        if(!validazioneOpt.isPresent())
            return new ResponseEntity<>("Non e' stata trovata nessuna validazione con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(validazioneOpt);
    }
}