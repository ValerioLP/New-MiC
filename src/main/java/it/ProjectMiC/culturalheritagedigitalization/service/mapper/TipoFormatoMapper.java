package it.ProjectMiC.culturalheritagedigitalization.service.mapper;

import it.ProjectMiC.culturalheritagedigitalization.entity.TipoFormatoEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.TipoFormato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoFormatoMapper {
    @Mapping(target = "fvpEntities", source = "tipoformato.fvps")
    TipoFormatoEntity toEntity(TipoFormato tipoformato);

    @Mapping(target = "fvps", source = "tipoFormatoEntity.fvpEntities")
    TipoFormato toModel(TipoFormatoEntity tipoFormatoEntity);

}
