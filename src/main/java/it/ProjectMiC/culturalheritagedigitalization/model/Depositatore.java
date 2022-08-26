package it.ProjectMiC.culturalheritagedigitalization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

@Data
public class Depositatore {

    private Long id_depositatore;
    private String uuid_provider;
    private String note;
    @JsonIgnore
    private FormatoValidazionePacchetto fvp;
    private Boolean stato;
    private Date createdAt;
    private Date lastUpdate;
}
