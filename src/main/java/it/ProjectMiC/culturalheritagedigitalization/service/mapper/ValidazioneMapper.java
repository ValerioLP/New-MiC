package it.ProjectMiC.culturalheritagedigitalization.service.mapper;

import it.ProjectMiC.culturalheritagedigitalization.entity.ValidazioneEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ValidazioneMapper {
    @Mapping(target = "fvpEntities", source = "validazione.fvps")
    ValidazioneEntity toEntity(Validazione validazione);
    @Mapping(target = "fvps", source = "validazioneEntity.fvpEntities")
    Validazione toModel(ValidazioneEntity validazioneEntity);

}
