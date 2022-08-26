package it.ProjectMiC.culturalheritagedigitalization.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatoPacchettoRequest {
    private String id_formato;
    private String uuid_provider;
    private String descrizione;
    private String id_tipo_formato;
    private String id_validazione;
    private String flussoNifi;
}
