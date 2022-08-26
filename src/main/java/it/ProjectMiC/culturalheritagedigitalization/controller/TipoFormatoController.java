package it.ProjectMiC.culturalheritagedigitalization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.ProjectMiC.culturalheritagedigitalization.model.APIResponse;
import it.ProjectMiC.culturalheritagedigitalization.model.TipoFormato;
import it.ProjectMiC.culturalheritagedigitalization.service.TipoFormatoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TipoFormatoController {
    private final TipoFormatoService tipoFormatoService;

    public TipoFormatoController(TipoFormatoService tipoFormatoService) {
        this.tipoFormatoService = tipoFormatoService;
    }
    @Operation(summary = "L’API readtipoformato consente di recuperare una o tutte le risorse presenti nella tabella tipo_formato.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoFormato.class))}),
    })
    @GetMapping("/readtipoformato")
    public ResponseEntity<?> readTipoFormatoById(@RequestParam(required = false) String id_tipo_formato) {
        if (id_tipo_formato == null)
            return new ResponseEntity<>(tipoFormatoService.findAll(), HttpStatus.OK);
        TipoFormato tipoFormato = tipoFormatoService.findById(id_tipo_formato);
        if (tipoFormato == null)
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id_tipo_formato, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(tipoFormato, HttpStatus.OK);
    }

    @GetMapping("/readtipoformato/{field}")
    public ResponseEntity<?> getWithSorting(@PathVariable String field) {
        switch (field) {
            case "descrizione":
            case "fvps":
            case "espressione":
            case "stato":
            case "createdAt":
            case "lastUpdate":
                break;
            default:
                return new ResponseEntity<>("Inserisci un field valido", HttpStatus.NOT_ACCEPTABLE);
        }
        List<TipoFormato> tipoformato = tipoFormatoService.findTipoFormatoBySorting(field);
        return new ResponseEntity<>(new APIResponse<>(tipoformato.size(), tipoformato),HttpStatus.OK);
    }

    @Operation(summary = "L’API readformato/pagination/{offset}/{pageSize} consente di recuperare tutte le risorse presenti nella tabella TIPO_FORMATO con la paginazione")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoFormato.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un pageSize maggiore di 0",
                    content = @Content)
    })
    @GetMapping("/readtipoformato/pagination/{offset}/{pageSize}\"")
    public ResponseEntity<?> getWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        if (pageSize < 1)
            return new ResponseEntity<>("Inserisci un pageSize maggiore di 0", HttpStatus.NOT_ACCEPTABLE);
        Page<TipoFormato> tipoformato = tipoFormatoService.findWithPagination(offset, pageSize);
        return new ResponseEntity<>(new APIResponse(tipoformato.getSize(), tipoformato), HttpStatus.OK);
    }

    @Operation(summary = "L’API createtipoformato consente di inserire una nuova risorsa. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "POST avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoFormato.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire tutti i campi richiesti",
                    content = @Content),
            @ApiResponse(responseCode = "406", description = "Limit di 250 char descrizione/espressione",
                    content = @Content)

    })
    @PostMapping("/createtipoformato")
    public ResponseEntity<?> createTipoFormato(@RequestBody TipoFormato tipoFormato) {
        String checkDescrizione = tipoFormato.getDescrizione();
        String checkEspressione = tipoFormato.getEspressione();

        if (checkDescrizione == null || checkDescrizione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo descrizione", HttpStatus.NOT_ACCEPTABLE);
        if (checkDescrizione.length() > 250)
            return new ResponseEntity<>("Limit descrizione di 250 char,sono stati inseriti:" + checkDescrizione.length(), HttpStatus.BAD_REQUEST);
        if (checkEspressione == null || checkEspressione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo espressione", HttpStatus.NOT_ACCEPTABLE);
        if (checkEspressione.length() > 250)
            return new ResponseEntity<>("Limit descrizione di 250 char,sono stati inseriti:" + checkEspressione.length(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tipoFormatoService.saveTipoFormato(tipoFormato), HttpStatus.CREATED);
    }

    @Operation(summary = "L’API deletetipoformato consente di eliminare una risorsa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DELETE avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoFormato.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Non e' stato trovato nessun tipo formato con id:",
                    content = @Content)})
    @DeleteMapping("/deletetipoformato")
    public ResponseEntity<?> deleteTipoFormato(@RequestParam String id) {
        if (id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire il parametro id", HttpStatus.NOT_ACCEPTABLE);
        Optional<TipoFormato> tipoFormato = Optional.ofNullable(tipoFormatoService.deleteTipoFormato(id));
        if (!tipoFormato.isPresent())
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(tipoFormato);
    }

    @Operation(summary = "L’API updateformatopacchetto consente di modificare una risorsa esistente e precedentemente creata con l’API createtipoformato.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UPDATE avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoFormato.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire nel body un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Non e' stato trovato nessun tipo formato con id: ",
                    content = @Content)
    })
    @PutMapping("/updatetipoformato")
    public ResponseEntity<?> updateTipoFormato(@RequestBody TipoFormato tipoFormato) {
        String id = tipoFormato.getId_tipo_formato();
        String checkDescrizione = tipoFormato.getDescrizione();
        String checkEspressione = tipoFormato.getEspressione();
        if (id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire nel body il campo id", HttpStatus.NOT_ACCEPTABLE);
        if (checkDescrizione.length() > 250)
            return new ResponseEntity<>("Limit descrizione di 250 char,sono stati inseriti:" + checkDescrizione.length(), HttpStatus.BAD_REQUEST);
        if (checkEspressione.length() > 250)
            return new ResponseEntity<>("Limit espressione di 250 char,sono stati inseriti:" + checkEspressione.length(), HttpStatus.BAD_REQUEST);
        Optional<TipoFormato> tipoFormatoOpt = Optional.ofNullable(tipoFormatoService.updateTipoFormato(tipoFormato));
        if (!tipoFormatoOpt.isPresent())
            return new ResponseEntity<>("Non e' stato trovato nessun tipo formato con id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.of(tipoFormatoOpt);
    }
}