package it.ProjectMiC.culturalheritagedigitalization.service.mapper;

import it.ProjectMiC.culturalheritagedigitalization.entity.DepositatoreEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.Depositatore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositatoreMapper {
    DepositatoreEntity toEntity(Depositatore depositatore);
    Depositatore toModel(DepositatoreEntity depositatoreEntity);

}
