package it.ProjectMiC.culturalheritagedigitalization.repository;

import it.ProjectMiC.culturalheritagedigitalization.entity.FormatoValidazionePacchettoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FormatoValidazionePacchettoRepository extends JpaRepository<FormatoValidazionePacchettoEntity, String> {
}
