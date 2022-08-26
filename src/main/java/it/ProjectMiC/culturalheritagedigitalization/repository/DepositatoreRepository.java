package it.ProjectMiC.culturalheritagedigitalization.repository;

import it.ProjectMiC.culturalheritagedigitalization.entity.DepositatoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepositatoreRepository extends JpaRepository<DepositatoreEntity, Long> {

    @Query("select d from DepositatoreEntity d where d.uuid_provider = ?1")
    public Optional<DepositatoreEntity> findByUuid_provider(String uuid_provider);
}
