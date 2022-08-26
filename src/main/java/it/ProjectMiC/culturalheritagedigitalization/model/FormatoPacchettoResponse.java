package it.ProjectMiC.culturalheritagedigitalization.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatoPacchettoResponse {
    private String id_formato_pacchetto;
    private Long id_depositatore;
    private String id_tipo_formato;
    private String desc_tipo_formato;
    private String id_validazione;
}
