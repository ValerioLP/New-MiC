package it.ProjectMiC.culturalheritagedigitalization.service;

import it.ProjectMiC.culturalheritagedigitalization.entity.TipoFormatoEntity;
import it.ProjectMiC.culturalheritagedigitalization.entity.ValidazioneEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.TipoFormato;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.repository.TipoFormatoRepository;
import it.ProjectMiC.culturalheritagedigitalization.service.mapper.TipoFormatoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TipoFormatoService {

    private final TipoFormatoMapper tipoFormatoMapper;
    private final TipoFormatoRepository tipoFormatoRepository;

    public TipoFormatoService(TipoFormatoMapper tipoFormatoMapper, TipoFormatoRepository tipoFormatoRepository) {
        this.tipoFormatoMapper = tipoFormatoMapper;
        this.tipoFormatoRepository = tipoFormatoRepository;
    }

    public final static Logger LOGGER = LoggerFactory.getLogger(TipoFormatoService.class);

    public List<TipoFormato> findAll() {
        return tipoFormatoRepository.findAll()
                .stream().map(tipoFormatoMapper::toModel).collect(Collectors.toList());
    }

    public TipoFormato findById(String id) {
        return tipoFormatoMapper.toModel(tipoFormatoRepository.findById(id).orElse(null));
    }

    public Page<TipoFormato> findWithPagination(int offset, int pageSize) {
        return tipoFormatoRepository.findAll(PageRequest.of(offset, pageSize))
                .map(tipoFormatoMapper::toModel);
    }

    public List<TipoFormato> findTipoFormatoBySorting(String field) {
        return tipoFormatoRepository.findAll(Sort.by(Sort.Direction.ASC, field)).stream()
                .map(tipoFormatoMapper::toModel).collect(Collectors.toList());
    }

    public TipoFormato saveTipoFormato(TipoFormato tipoFormato) {
        TipoFormatoEntity tipoFormatoEntity = tipoFormatoMapper.toEntity(tipoFormato);
        tipoFormatoEntity.setId_tipo_formato(UUID.randomUUID().toString());
        tipoFormatoEntity.setStato(true);
        return tipoFormatoMapper.toModel(tipoFormatoRepository.save(tipoFormatoEntity));
    }

    @Transactional
    public TipoFormato updateTipoFormato(TipoFormato tipoFormato) {
        Optional<TipoFormatoEntity> tipoFormatoOldOpt = tipoFormatoRepository.findById(tipoFormato.getId_tipo_formato());
        if (tipoFormatoOldOpt.isPresent()) {
            TipoFormatoEntity tipoFormatoOld = tipoFormatoOldOpt.get();
            TipoFormatoEntity tipoFormatoNew = new TipoFormatoEntity();

            if(tipoFormato.getDescrizione() != null)
                tipoFormatoNew.setDescrizione(tipoFormato.getDescrizione());
            else tipoFormatoNew.setDescrizione(tipoFormatoOld.getDescrizione());
            if(tipoFormato.getEspressione() != null)
                tipoFormatoNew.setEspressione(tipoFormato.getEspressione());
            else tipoFormatoNew.setEspressione(tipoFormatoOld.getEspressione());

            tipoFormatoNew.setId_tipo_formato(tipoFormatoOld.getId_tipo_formato());
            tipoFormatoNew.setCreatedAt(tipoFormatoOld.getCreatedAt());
            tipoFormatoNew.setLastUpdate(new Date());
            tipoFormatoNew.setStato(true);
            return tipoFormatoMapper.toModel(tipoFormatoRepository.save(tipoFormatoNew));
        } else {
            LOGGER.error("Non esiste una validazione con l'id " + tipoFormato.getId_tipo_formato());
            return null;
        }
    }

    public TipoFormato deleteTipoFormato(String id) {
        Optional<TipoFormatoEntity> entityOpt = tipoFormatoRepository.findById(id);
        if (entityOpt.isPresent()) {
            TipoFormatoEntity newEntity = entityOpt.get();
            tipoFormatoRepository.delete(newEntity);
            return tipoFormatoMapper.toModel(newEntity);
        } else {
            LOGGER.error("Non esiste un tipo formato con l'id " + id);
            return null;
        }
    }
}