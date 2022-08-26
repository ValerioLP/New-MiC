package it.ProjectMiC.culturalheritagedigitalization.service;

import it.ProjectMiC.culturalheritagedigitalization.entity.FormatoValidazionePacchettoEntity;
import it.ProjectMiC.culturalheritagedigitalization.entity.TipoFormatoEntity;
import it.ProjectMiC.culturalheritagedigitalization.entity.ValidazioneEntity;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoPacchettoRequest;
import it.ProjectMiC.culturalheritagedigitalization.model.FormatoValidazionePacchetto;
import it.ProjectMiC.culturalheritagedigitalization.model.Validazione;
import it.ProjectMiC.culturalheritagedigitalization.repository.FormatoValidazionePacchettoRepository;
import it.ProjectMiC.culturalheritagedigitalization.repository.TipoFormatoRepository;
import it.ProjectMiC.culturalheritagedigitalization.repository.ValidazioneRepository;
import it.ProjectMiC.culturalheritagedigitalization.service.mapper.FormatoValidazionePacchettoMapper;
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
public class FormatoValidazionePacchettoService {
    private final FormatoValidazionePacchettoMapper formatoValidazionePacchettoMapper;
    private final FormatoValidazionePacchettoRepository formatoValidazionePacchettoRepository;
    private final ValidazioneRepository validazioneRepository;
    private final TipoFormatoRepository tipoFormatoRepository;

    public FormatoValidazionePacchettoService(FormatoValidazionePacchettoMapper formatoValidazionePacchettoMapper,
                                              FormatoValidazionePacchettoRepository formatoValidazionePacchettoRepository,
                                              ValidazioneRepository validazioneRepository,
                                              TipoFormatoRepository tipoFormatoRepository) {
        this.formatoValidazionePacchettoMapper = formatoValidazionePacchettoMapper;
        this.formatoValidazionePacchettoRepository = formatoValidazionePacchettoRepository;
        this.validazioneRepository = validazioneRepository;
        this.tipoFormatoRepository = tipoFormatoRepository;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(FormatoValidazionePacchettoService.class);

    public FormatoValidazionePacchetto saveFormatoPacchetto(FormatoPacchettoRequest formatoPacchetto) {
        Optional<ValidazioneEntity> validazioneEntity = validazioneRepository.findById(formatoPacchetto.getId_validazione());
        if(!validazioneEntity.isPresent()) {
            LOGGER.info("Non e' stata trovata nessuna validazione con id: " + formatoPacchetto.getId_validazione());
        }

        Optional<TipoFormatoEntity> tipoFormatoEntity = tipoFormatoRepository.findById(formatoPacchetto.getId_tipo_formato());
        if(!tipoFormatoEntity.isPresent()) {
            LOGGER.info("Non e' stato trovato nessun tipo formato con id: " + formatoPacchetto.getId_tipo_formato());
        }

        FormatoValidazionePacchettoEntity fvpEntity = new FormatoValidazionePacchettoEntity();
        fvpEntity.setId_formato(UUID.randomUUID().toString());
        //fvpEntity.setId_depositatore(formatoPacchetto.getId_depositatore());
        fvpEntity.setDescrizione(formatoPacchetto.getDescrizione());
        validazioneEntity.ifPresent(fvpEntity::setValidazioneEntity);
        tipoFormatoEntity.ifPresent(fvpEntity::setTipoFormatoEntity);
        fvpEntity.setStato(true);

        if(fvpEntity.getValidazioneEntity() == null || fvpEntity.getTipoFormatoEntity() == null)
            return formatoValidazionePacchettoMapper.toModel(fvpEntity);
        return formatoValidazionePacchettoMapper.toModel(formatoValidazionePacchettoRepository.save(fvpEntity));
    }

    public List<FormatoValidazionePacchetto> findAll() {
        return formatoValidazionePacchettoRepository.findAll()
                .stream().map(formatoValidazionePacchettoMapper::toModel).collect(Collectors.toList());
    }

    public FormatoValidazionePacchetto findById(String id) {
        return formatoValidazionePacchettoMapper.toModel(formatoValidazionePacchettoRepository.findById(id).orElse(null));
    }

    public List<FormatoValidazionePacchetto> getWithSorting(String field) {
        return formatoValidazionePacchettoRepository.findAll(Sort.by(Sort.Direction.ASC, field))
                .stream()
                .map(formatoValidazionePacchettoMapper::toModel).collect(Collectors.toList());
    }

    public Page<FormatoValidazionePacchetto> findWithPagination(int offset, int pagesize) {
        return formatoValidazionePacchettoRepository.findAll(PageRequest.of(offset, pagesize))
                .map(formatoValidazionePacchettoMapper::toModel);
    }

    @Transactional
    public FormatoValidazionePacchetto updateFormatoValidazionePacchetto(FormatoPacchettoRequest formatoPacchetto) {
        Optional<FormatoValidazionePacchettoEntity> entityOld = formatoValidazionePacchettoRepository.findById(formatoPacchetto.getId_formato());
        if (entityOld.isPresent()) {
            FormatoValidazionePacchettoEntity fvpEntity = new FormatoValidazionePacchettoEntity();
            TipoFormatoEntity tipoFormatoEntity = new TipoFormatoEntity();
            ValidazioneEntity validazioneEntity = new ValidazioneEntity();

            if(formatoPacchetto.getId_tipo_formato() != null) {
                Optional<TipoFormatoEntity> tipoFormatoEntityOpt = tipoFormatoRepository.findById(formatoPacchetto.getId_tipo_formato());
                if(!tipoFormatoEntityOpt.isPresent()) {
                    LOGGER.info("Non e' stato trovato nessun tipo formato con id: " + formatoPacchetto.getId_tipo_formato());
                    tipoFormatoEntity = null;
                } else tipoFormatoEntity = tipoFormatoEntityOpt.get();
            }
            else tipoFormatoEntity = entityOld.get().getTipoFormatoEntity();

            if(formatoPacchetto.getId_validazione() != null) {
                Optional<ValidazioneEntity> validazioneEntityOpt = validazioneRepository.findById(formatoPacchetto.getId_validazione());
                if(!validazioneEntityOpt.isPresent()) {
                    LOGGER.info("Non e' stata trovata nessuna validazione con id: " + formatoPacchetto.getId_validazione());
                    validazioneEntity = null;
                } else validazioneEntity = validazioneEntityOpt.get();
            }
            else validazioneEntity = entityOld.get().getValidazioneEntity();

            /*if(formatoPacchetto.getId_depositatore() != null)
                fvpEntity.setId_depositatore(formatoPacchetto.getId_depositatore());
            else fvpEntity.setId_depositatore(entityOld.get().getId_depositatore());*/

            if(formatoPacchetto.getDescrizione() != null)
                fvpEntity.setDescrizione(formatoPacchetto.getDescrizione());
            else fvpEntity.setDescrizione(entityOld.get().getDescrizione());

            fvpEntity.setId_formato(entityOld.get().getId_formato());
            fvpEntity.setValidazioneEntity(validazioneEntity);
            fvpEntity.setTipoFormatoEntity(tipoFormatoEntity);
            fvpEntity.setCreatedAt(entityOld.get().getCreatedAt());
            fvpEntity.setLastUpdate(new Date());
            fvpEntity.setStato(true);

            if(fvpEntity.getValidazioneEntity() == null || fvpEntity.getTipoFormatoEntity() == null)
                return formatoValidazionePacchettoMapper.toModel(fvpEntity);
            return formatoValidazionePacchettoMapper.toModel(formatoValidazionePacchettoRepository.save(fvpEntity));
        } else {
            LOGGER.error("Non esiste un FormatoValidazionePacchetto con l'id " + formatoPacchetto.getId_formato());
            return null;
        }
    }

    public FormatoValidazionePacchetto deleteFormatoValidazionePacchetto(String id) {
        Optional<FormatoValidazionePacchettoEntity> entityOpt = formatoValidazionePacchettoRepository.findById(id);
        if (entityOpt.isPresent()) {
            FormatoValidazionePacchettoEntity entity = entityOpt.get();
            formatoValidazionePacchettoRepository.delete(entity);
            return formatoValidazionePacchettoMapper.toModel(entity);
        } else {
            LOGGER.error("Non esiste un FormatoValidazionePacchetto con l'id " + id);
            return null;
        }

    }
}