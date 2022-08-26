package it.ProjectMiC.culturalheritagedigitalization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.ProjectMiC.culturalheritagedigitalization.model.APIResponse;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoValidazionePacchetto;
import it.ProjectMiC.culturalheritagedigitalization.model.TipoFormato;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.service.ValidazioneService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ValidazioneController {

    private final ValidazioneService validazioneService;

    public ValidazioneController(ValidazioneService validazioneService) {
        this.validazioneService = validazioneService;
    }
    @Operation(summary = "L’API readvalidazione consente di recuperare una o tutte le risorse presenti nella tabella validazione")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Validazione.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content)
    })
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

    @Operation(summary = "L’API readvalidazione/pagination/{offset}/{pageSize} consente di recuperare tutte le risorse presenti nella tabella validazione con la paginazione")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Validazione.class))}),
            @ApiResponse(responseCode = "404", description = "Si prega di inserire pagesize",
                    content = @Content)
    })
    @GetMapping("/readvalidazione/pagination/{offset}/{pageSize}\"")
    public ResponseEntity<? >getWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        if(pageSize<1)
            return new ResponseEntity<>("Page size deve essere maggiore di 0",HttpStatus.NOT_ACCEPTABLE);
        Page<Validazione> validazione = validazioneService.findWithPagination(offset, pageSize);
        return new ResponseEntity<>(new APIResponse<>(validazione.getSize(), validazione),HttpStatus.OK);
    }
    @Operation(summary = "L’API createvalidazione consente di inserire una nuova risorsa. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "POST avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Validazione.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "L'id inserito non e stato trovato",
                    content = @Content)})
    @PostMapping("/createvalidazione")
    public ResponseEntity<?> createValidazione(@RequestBody Validazione validazione) {
        String checkDescrizione = validazione.getDescrizione();
        String checkEspressione = validazione.getEspressione();

        if(checkDescrizione == null || checkDescrizione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo descrizione", HttpStatus.NOT_ACCEPTABLE);
        if(checkDescrizione.length()>250)
            return new ResponseEntity<>("Limit descrizione di 250 char",HttpStatus.BAD_REQUEST);
        if(checkEspressione == null || checkEspressione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo espressione", HttpStatus.NOT_ACCEPTABLE);
        if(checkEspressione.length()>250)
            return new ResponseEntity<>("Limit espressione di 250 char",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(validazioneService.saveValidazione(validazione), HttpStatus.CREATED);
    }
    @Operation(summary = "L’API deletevalidazione consente di eliminare una risorsa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DELETE avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Validazione.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
            })
    @DeleteMapping("/deletevalidazione")
    public ResponseEntity<?> deleteValidazione(@RequestParam String id) {
        if(id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire il parametro id", HttpStatus.NOT_ACCEPTABLE);
        Optional<Validazione> validazione = Optional.ofNullable(validazioneService.deleteValidazione(id));
        if(!validazione.isPresent())
            return new ResponseEntity<>("Non e' stata trovata nessuna validazione con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(validazione);
    }
    @Operation(summary = "L’API updatevalidazione consente di modificare una risorsa esistente e precedentemente creata con l’API createvalidazione.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UPDATE avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Validazione.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire nel body un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Non e' stato trovato nessun tipo formato con id: ",
                    content = @Content)
    })
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