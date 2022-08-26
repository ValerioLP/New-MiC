package it.ProjectMiC.culturalheritagedigitalization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Validazione {
    private String id_validazione;
    private String descrizione;
    private String espressione;
    @JsonIgnore
    private List<FormatoValidazionePacchetto> fvps;
    private Boolean stato;
    private Date createdAt;
    private Date lastUpdate;
}
