package it.ProjectMiC.culturalheritagedigitalization.repository;

import it.ProjectMiC.culturalheritagedigitalization.entity.ValidazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValidazioneRepository extends JpaRepository<ValidazioneEntity, String> {
}
