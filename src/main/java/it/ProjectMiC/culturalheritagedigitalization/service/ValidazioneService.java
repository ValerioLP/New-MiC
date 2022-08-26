package it.ProjectMiC.culturalheritagedigitalization.service;


import it.ProjectMiC.culturalheritagedigitalization.entity.ValidazioneEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.repository.ValidazioneRepository;
import it.ProjectMiC.culturalheritagedigitalization.service.mapper.ValidazioneMapper;
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
public class ValidazioneService {
    private final ValidazioneMapper validazioneMapper;
    private final ValidazioneRepository validazioneRepository;

    public ValidazioneService(ValidazioneMapper validazioneMapper, ValidazioneRepository validazioneRepository) {
        this.validazioneMapper = validazioneMapper;
        this.validazioneRepository = validazioneRepository;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(ValidazioneService.class);

    public List<Validazione> findAll() {
        return validazioneRepository.findAll()
                .stream().map(validazioneMapper::toModel).collect(Collectors.toList());
    }

    public Validazione findById(String id) {
        return validazioneMapper.toModel(validazioneRepository.findById(id).orElse(null));
    }

    public List<Validazione> findValidazioneBySorting(String field){
        return validazioneRepository.findAll(Sort.by(Sort.Direction.ASC,field))
                .stream()
                .map(validazioneMapper::toModel).collect(Collectors.toList());
    }

    public Page<Validazione> findWithPagination(int offset, int pageSize){
        return validazioneRepository.findAll(PageRequest.of(offset,pageSize))
                .map(validazioneMapper::toModel);
    }

    public Validazione saveValidazione(Validazione validazione) {
        ValidazioneEntity validazioneEntity = validazioneMapper.toEntity(validazione);
        validazioneEntity.setId_validazione(UUID.randomUUID().toString());
        validazioneEntity.setStato(true);
        return validazioneMapper.toModel(validazioneRepository.save(validazioneEntity));
    }

    @Transactional
    public Validazione updateValidazione(Validazione validazione) {
        Optional<ValidazioneEntity> validazioneOldOpt = validazioneRepository.findById(validazione.getId_validazione());
        if (validazioneOldOpt.isPresent()) {
            ValidazioneEntity validazioneOld = validazioneOldOpt.get();
            ValidazioneEntity validazioneNew = new ValidazioneEntity();

            if(validazione.getDescrizione() != null)
                validazioneNew.setDescrizione(validazione.getDescrizione());
            else validazioneNew.setDescrizione(validazioneOld.getDescrizione());
            if(validazione.getEspressione() != null)
                validazioneNew.setEspressione(validazione.getEspressione());
            else validazioneNew.setEspressione(validazioneOld.getEspressione());

            validazioneNew.setId_validazione(validazioneOld.getId_validazione());
            validazioneNew.setCreatedAt(validazioneOld.getCreatedAt());
            validazioneNew.setLastUpdate(new Date());
            validazioneNew.setStato(true);
            return validazioneMapper.toModel(validazioneRepository.save(validazioneNew));
        } else {
            LOGGER.error("Non esiste una validazione con l'id " + validazione.getId_validazione());
            return null;
        }
    }

    public Validazione deleteValidazione(String id) {
        Optional<ValidazioneEntity> entityOpt = validazioneRepository.findById(id);
        if (entityOpt.isPresent()) {
            ValidazioneEntity entity = entityOpt.get();
            validazioneRepository.delete(entity);
            return validazioneMapper.toModel(entity);
        } else {
            LOGGER.error("Non esiste una validazione con l'id " + id);
            return null;
        }

    }

}