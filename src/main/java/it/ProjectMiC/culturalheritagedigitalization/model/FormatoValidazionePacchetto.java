package it.ProjectMiC.culturalheritagedigitalization.model;

import lombok.Data;

import java.util.Date;

@Data
public class FormatoValidazionePacchetto {
    private String id_formato;
    private String uuid_provider;
    private String descrizione;
    private Depositatore depositatore;
    private TipoFormato tipoFormato;
    private Validazione validazione;
    private String flussoNifi;
    private Boolean stato;
    private Date createdAt;
    private Date lastUpdate;
}
