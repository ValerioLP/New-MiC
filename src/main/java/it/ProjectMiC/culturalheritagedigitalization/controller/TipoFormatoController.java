package it.ProjectMiC.culturalheritagedigitalization.controller;

import it.ProjectMiC.culturalheritagedigitalization.model.APIResponse;
import it.ProjectMiC.culturalheritagedigitalization.model.TipoFormato;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.service.TipoFormatoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TipoFormatoController {
    private final TipoFormatoService tipoFormatoService;

    public TipoFormatoController(TipoFormatoService tipoFormatoService) {
        this.tipoFormatoService = tipoFormatoService;
    }

    @GetMapping("/readtipoformato")
    public ResponseEntity<?> readTipoFormatoById(@RequestParam(required = false) String id_tipo_formato) {
        if(id_tipo_formato == null)
            return new ResponseEntity<>(tipoFormatoService.findAll(), HttpStatus.OK);
        TipoFormato tipoFormato = tipoFormatoService.findById(id_tipo_formato);
        if(tipoFormato == null)
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id_tipo_formato, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(tipoFormato, HttpStatus.OK);
    }

    @GetMapping("/readtipoformato/{field}")
    public APIResponse<List<TipoFormato>> getWithSorting(@PathVariable String field){
        List<TipoFormato> tipoformato = tipoFormatoService.findTipoFormatoBySorting(field);
        return new APIResponse<>(tipoformato.size(), tipoformato);
    }

    @GetMapping("/readtipoformato/pagination/{offset}/{pageSize}\"")
    public APIResponse<Page<TipoFormato>> getWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<TipoFormato> tipoformato = tipoFormatoService.findWithPagination(offset, pageSize);
        return new APIResponse<>(tipoformato.getSize(), tipoformato);
    }

    @PostMapping("/createtipoformato")
    public ResponseEntity<?> createTipoFormato(@RequestBody TipoFormato tipoFormato) {
        String checkDescrizione = tipoFormato.getDescrizione();
        String checkEspressione = tipoFormato.getEspressione();

        if(checkDescrizione == null || checkDescrizione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo descrizione", HttpStatus.NOT_ACCEPTABLE);
        if(checkEspressione == null || checkEspressione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo espressione", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(tipoFormatoService.saveTipoFormato(tipoFormato), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletetipoformato")
    public ResponseEntity<?> deleteTipoFormato(@RequestParam String id) {
        if(id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire il parametro id", HttpStatus.NOT_ACCEPTABLE);
        Optional<TipoFormato> tipoFormato = Optional.ofNullable(tipoFormatoService.deleteTipoFormato(id));
        if(!tipoFormato.isPresent())
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(tipoFormato);
    }

    @PutMapping("/updatetipoformato")
    public ResponseEntity<?> updateTipoFormato(@RequestBody TipoFormato tipoFormato) {
        String id = tipoFormato.getId_tipo_formato();
        if(id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire nel body il campo id", HttpStatus.NOT_ACCEPTABLE);
        Optional<TipoFormato> tipoFormatoOpt = Optional.ofNullable(tipoFormatoService.updateTipoFormato(tipoFormato));
        if(!tipoFormatoOpt.isPresent())
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(tipoFormatoOpt);
    }
}