package it.ProjectMiC.culturalheritagedigitalization.service.mapper;

import it.ProjectMiC.culturalheritagedigitalization.entity.FormatoValidazionePacchettoEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoValidazionePacchetto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormatoValidazionePacchettoMapper {

    @Mapping(target = "tipoFormatoEntity", source = "formatoValidazionePacchetto.tipoFormato")
    @Mapping(target = "validazioneEntity", source = "formatoValidazionePacchetto.validazione")
    FormatoValidazionePacchettoEntity toEntity(FormatoValidazionePacchetto formatoValidazionePacchetto);

    @Mapping(target = "tipoFormato", source = "formatoValidazionePacchettoEntity.tipoFormatoEntity")
    @Mapping(target = "validazione", source = "formatoValidazionePacchettoEntity.validazioneEntity")
    FormatoValidazionePacchetto toModel(FormatoValidazionePacchettoEntity formatoValidazionePacchettoEntity);
}
