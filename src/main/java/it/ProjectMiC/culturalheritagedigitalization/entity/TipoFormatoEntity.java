package it.ProjectMiC.culturalheritagedigitalization.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tipo_Formato_Pacchetto")
public class TipoFormatoEntity {

    @Id
    @Column(name = "Id_Tipo_Formato", updatable = false, nullable = false, length = 50)
    private String id_tipo_formato;

    @Column(name = "Descrizione")
    private String descrizione;

    @Column(name = "Espressione")
    private String espressione;

    @OneToMany(mappedBy = "tipoFormatoEntity", cascade = CascadeType.ALL)
    private List<FormatoValidazionePacchettoEntity> fvpEntities;

    @Column(name = "Stato")
    private Boolean stato;

    @Column(name = "Data_Inserimento", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "Data_Modifica")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdate;
}
