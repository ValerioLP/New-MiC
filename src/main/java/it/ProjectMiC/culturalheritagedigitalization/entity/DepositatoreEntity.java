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
@Table(name = "Depositatore")
public class DepositatoreEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Depositatore", updatable = false, nullable = false)
    private Long id_depositatore;

    @Column(name = "Id_Provider_PDND", updatable = false, nullable = false, length = 50)
    private String uuid_provider;

    @Column(name = "Note")
    private String note;

    @OneToOne(mappedBy = "depositatoreEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FormatoValidazionePacchettoEntity fvpE;

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
