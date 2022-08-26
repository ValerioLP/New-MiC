package it.ProjectMiC.culturalheritagedigitalization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.ProjectMiC.culturalheritagedigitalization.model.APIResponse;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoPacchettoRequest;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoValidazionePacchetto;
import it.ProjectMiC.culturalheritagedigitalization.service.FormatoValidazionePacchettoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FVPacchettoController {

    private final FormatoValidazionePacchettoService formatoValidazionePacchettoService;

    public FVPacchettoController(FormatoValidazionePacchettoService formatoValidazionePacchettoService) {
        this.formatoValidazionePacchettoService = formatoValidazionePacchettoService;
    }

    @Operation(summary = "L’API readformatopacchetto consente di recuperare una o tutte le risorse presenti nella tabella formato_validazione_pacchetto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormatoValidazionePacchetto.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content)
    })
    @GetMapping("/readformatopacchetto")
    public ResponseEntity<?> readFormatoPacchetto(@RequestParam(required = false) String id_formato) {
        if (id_formato == null)
            return new ResponseEntity<>(formatoValidazionePacchettoService.findAll(), HttpStatus.OK);
        FormatoValidazionePacchetto fvp = formatoValidazionePacchettoService.findById(id_formato);
        if (fvp == null)
            return new ResponseEntity<>("Non e' stato trovato nessun formato con id: " + id_formato, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(fvp, HttpStatus.OK);
    }


    @GetMapping("/readformatopacchetto/{field}")
    public APIResponse<List<FormatoValidazionePacchetto>> getWithSorting(@PathVariable String field) {
        List<FormatoValidazionePacchetto> formatoValidazioenPacchetto = formatoValidazionePacchettoService.getWithSorting(field);
        return new APIResponse<>(formatoValidazioenPacchetto.size(), formatoValidazioenPacchetto);
    }

    @Operation(summary = "L’API readformatopacchetto/pagination/{offset}/{pageSize} consente di recuperare tutte le risorse presenti nella tabella FORMATO_VALIDAZIONE_PACCHETTO con la paginazione")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormatoValidazionePacchetto.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire offset & pagesize",
                    content = @Content)
    })
    @GetMapping("/readformatopacchetto/pagination/{offset}/{pageSize}\"")
    public ResponseEntity<?> getWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        if (pageSize < 1)
            return new ResponseEntity<>("Page size deve essere maggiore di 0", HttpStatus.NOT_ACCEPTABLE);
        Page<FormatoValidazionePacchetto> formatoValidazionePacchetto = formatoValidazionePacchettoService.findWithPagination(offset, pageSize);
        return new ResponseEntity<>(new APIResponse<>(formatoValidazionePacchetto.getSize(), formatoValidazionePacchetto), HttpStatus.OK);
    }

    @Operation(summary = "L’API createformatopacchetto consente di inserire una nuova risorsa. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "POST avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormatoValidazionePacchetto.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "L'id inserito non e stato trovato",
                    content = @Content)})
    @PostMapping("/createformatopacchetto")
    public ResponseEntity<?> createFormatoPacchetto(@RequestBody FormatoPacchettoRequest formatoPacchetto) {
        String checkDescrizione = formatoPacchetto.getDescrizione();
        String checkIdValidazione = formatoPacchetto.getId_validazione();
        String checkIdTipoFormato = formatoPacchetto.getId_tipo_formato();
        //String checkIdDepositatore = formatoPacchetto.getId_depositatore();

        if (checkDescrizione == null || checkDescrizione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo descrizione", HttpStatus.NOT_ACCEPTABLE);
        if (checkDescrizione.length() > 250)
            return new ResponseEntity<>("Limit descrizione di 250 char, sono stati inseriti" + checkDescrizione.length(), HttpStatus.BAD_REQUEST);
        if (checkIdValidazione == null || checkIdValidazione.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo id_validazione", HttpStatus.NOT_ACCEPTABLE);
        if (checkIdTipoFormato == null || checkIdTipoFormato.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo id_tipo_formato", HttpStatus.NOT_ACCEPTABLE);
        /*if(checkIdDepositatore == null || checkIdDepositatore.equals(""))
            return new ResponseEntity<>("Si prega di inserire il campo id_depositatore", HttpStatus.NOT_ACCEPTABLE);*/

        FormatoValidazionePacchetto fvp = formatoValidazionePacchettoService.saveFormatoPacchetto(formatoPacchetto);
        if (fvp.getTipoFormato() == null)
            return new ResponseEntity<>("Non e' stato trovato nessun id_formato: " + checkIdTipoFormato, HttpStatus.NOT_ACCEPTABLE);
        if (fvp.getValidazione() == null)
            return new ResponseEntity<>("Non e' stato trovato nessun id_validazione: " + checkIdValidazione, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(fvp, HttpStatus.CREATED);
    }

    @Operation(summary = "L’API deleteformatopacchetto consente di eliminare una risorsa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DELETE avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormatoValidazionePacchetto.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
    })
    @DeleteMapping("/deleteformatopacchetto")
    public ResponseEntity<?> deleteFormatoValidazionePacchetto(@RequestParam String id_formato) {
        if (id_formato == null || id_formato.equals(""))
            return new ResponseEntity<>("Si prega di inserire il parametro id", HttpStatus.NOT_ACCEPTABLE);
        Optional<FormatoValidazionePacchetto> fvpOpt = Optional.ofNullable(formatoValidazionePacchettoService.deleteFormatoValidazionePacchetto(id_formato));
        if (!fvpOpt.isPresent())
            return new ResponseEntity<>("Non e' stato trovato nessun formato con id: " + id_formato, HttpStatus.NOT_ACCEPTABLE);
        return ResponseEntity.of(fvpOpt);
    }
    @Operation(summary = "L’API updateformatopacchetto consente di modificare una risorsa esistente e precedentemente creata con l’API createformatopacchetto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update avvenuto con successo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FormatoValidazionePacchetto.class))}),
            @ApiResponse(responseCode = "406", description = "Si prega di inserire un id valido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "L'id inserito non e stato trovato",
                    content = @Content)})
    @PutMapping("/updateformatopacchetto")
    public ResponseEntity<?> updateFormatoValidazionePacchetto(@RequestBody FormatoPacchettoRequest formatoPacchetto) {
        String id = formatoPacchetto.getId_formato();
        if (id == null || id.equals(""))
            return new ResponseEntity<>("Si prega di inserire nel body il campo id", HttpStatus.NOT_ACCEPTABLE);

        Optional<FormatoValidazionePacchetto> fvpOpt = Optional.ofNullable(formatoValidazionePacchettoService.updateFormatoValidazionePacchetto(formatoPacchetto));

        if (fvpOpt.isPresent()) {
            if (fvpOpt.get().getValidazione() == null)
                return new ResponseEntity<>("Non e' stato trovato nessun id_validazione: " + formatoPacchetto.getId_validazione(), HttpStatus.NOT_ACCEPTABLE);
            if (fvpOpt.get().getTipoFormato() == null)
                return new ResponseEntity<>("Non e' stato trovato nessun id_formato: " + formatoPacchetto.getId_tipo_formato(), HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<>(fvpOpt, HttpStatus.CREATED);
        } else return new ResponseEntity<>("Non e' stato trovato nessun formato con id: " + id, HttpStatus.NOT_FOUND);
    }
}