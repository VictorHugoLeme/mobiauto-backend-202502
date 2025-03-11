package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeFinishDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeTransferDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeUpdateDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.Cargo;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
import dev.victorhleme.mobiauto.repositories.OportunidadeRepository;
import dev.victorhleme.mobiauto.repositories.specifications.OportunidadeSpecifications;
import dev.victorhleme.mobiauto.services.OportunidadeService;
import dev.victorhleme.mobiauto.services.RevendaService;
import dev.victorhleme.mobiauto.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OportunidadeServiceImpl implements OportunidadeService {

    private final OportunidadeMapper oportunidadeMapper;
    private final OportunidadeRepository oportunidadeRepository;
    private final OportunidadeSpecifications oportunidadeSpecifications;
    private final UsuarioService usuarioService;
    private final RevendaService revendaService;

    @Override
    public Oportunidade save(OportunidadeCreationDto oportunidadeDto) {
        Oportunidade newOportunidade = oportunidadeMapper.from(oportunidadeDto);
        newOportunidade.setRevenda(revendaService.findById(oportunidadeDto.getRevendaId()));
        assignOportunidade(newOportunidade);
        return oportunidadeRepository.save(newOportunidade);
    }

    @Override
    public Page<Oportunidade> findAll(OportunidadeFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return oportunidadeRepository.findAll(oportunidadeSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public Oportunidade findById(Long id) {
        return oportunidadeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Oportunidade.class, id));
    }

    @Override
    public Oportunidade transfer(OportunidadeTransferDto transferDto) {
        Oportunidade existing = findById(transferDto.getId());
        Usuario newResponsavel = usuarioService.findById(transferDto.getNewResponsavelId());
        existing.setResponsavel(newResponsavel);
        existing.setDataAtribuicao(LocalDateTime.now());
        return oportunidadeRepository.save(existing);
    }

    @Override
    public Oportunidade finish(OportunidadeFinishDto finishDto) {
        Oportunidade existing = findById(finishDto.getId());
        existing.setDataConclusao(LocalDateTime.now());
        BeanUtils.copyProperties(finishDto, existing);
        return oportunidadeRepository.save(existing);
    }

    @Override
    public Oportunidade update(OportunidadeUpdateDto oportunidadeDto) {
        Oportunidade existing = findById(oportunidadeDto.getId());

        BeanUtils.copyProperties(oportunidadeDto, existing);
        return oportunidadeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        oportunidadeRepository.deleteById(id);
    }

    private void assignOportunidade(Oportunidade oportunidade) {
        UsuarioFilter filter = new UsuarioFilter().withCargo(Cargo.ASSISTENTE).withRevendaId(oportunidade.getRevenda().getId());
        List<Usuario> assistants = usuarioService.findAll(filter);

        if (assistants.isEmpty())
            throw new IllegalStateException("This Revenda has no Assistants.");

        assistants.sort(Comparator.comparingLong(this::countOngoingOpportunities)
            .thenComparing(this::getLastOpportunityAssignedTime));

        Usuario selectedAssistant = assistants.getFirst();

        oportunidade.setResponsavel(selectedAssistant);
        oportunidade.setDataAtribuicao(LocalDateTime.now());

        log.info("Assigned oportunidade with Id {} to usuário with Id {} (revenda Id {})",
            oportunidade.getId(), selectedAssistant.getId(), oportunidade.getRevenda().getId());
    }

    private long countOngoingOpportunities(Usuario usuario) {
        return oportunidadeRepository.countByResponsavel(usuario);
    }

    private LocalDateTime getLastOpportunityAssignedTime(Usuario usuario) {
        return oportunidadeRepository.findTopByResponsavelOrderByDataAtribuicaoDesc(usuario)
            .map(Oportunidade::getDataAtribuicao)
            .orElse(LocalDateTime.MIN);
    }
}
