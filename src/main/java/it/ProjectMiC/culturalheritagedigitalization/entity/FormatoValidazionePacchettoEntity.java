package it.ProjectMiC.culturalheritagedigitalization.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Formato_Validazione_Pacchetto")
public class FormatoValidazionePacchettoEntity {

    @Id
    @Column(name = "Id_Formato_Validazione", updatable = false, nullable = false, length = 50)
    private String id_formato;

    @Column(name = "Descrizione", nullable = false)
    private String descrizione;

    @Column(name = "Id_Provider_PDND", updatable = false, nullable = false, length = 50)
    private String uuid_provider;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_depositatore", referencedColumnName = "Id_Depositatore")
    private DepositatoreEntity depositatoreEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_validazione", referencedColumnName = "Id_Validazione")
    private ValidazioneEntity validazioneEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_formato", referencedColumnName = "Id_Tipo_Formato")
    private TipoFormatoEntity tipoFormatoEntity;

    @Column(name = "FlussoNIFI")
    private String flussoNifi;

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
