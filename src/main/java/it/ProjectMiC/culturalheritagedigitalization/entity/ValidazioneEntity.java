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
@Table(name = "Validazione")
public class ValidazioneEntity {

    @Id
    @Column(name = "Id_Validazione", updatable = false, nullable = false, length = 50)
    private String id_validazione;

    @Column(name = "Descrizione", nullable = false)
    private String descrizione;

    @Column(name = "Espressione")
    private String espressione;

    @OneToMany(mappedBy = "validazioneEntity", cascade = CascadeType.MERGE)
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
