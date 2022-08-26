package it.ProjectMiC.culturalheritagedigitalization.repository;

import it.ProjectMiC.culturalheritagedigitalization.entity.TipoFormatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoFormatoRepository extends JpaRepository<TipoFormatoEntity, String> {
}
