package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
import dev.victorhleme.mobiauto.repositories.OportunidadeRepository;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.repositories.specifications.OportunidadeSpecifications;
import dev.victorhleme.mobiauto.services.OportunidadeService;
import dev.victorhleme.mobiauto.services.RevendaService;
import dev.victorhleme.mobiauto.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.text.MessageFormat.format;

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
        newOportunidade.setResponsavel(usuarioService.findById(oportunidadeDto.getResponsavelId()));
        newOportunidade.setRevenda(revendaService.findById(oportunidadeDto.getRevendaId()));
        return oportunidadeRepository.save(newOportunidade);
    }

    @Override
    public Page<Oportunidade> getAll(OportunidadeFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return oportunidadeRepository.findAll(oportunidadeSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public Oportunidade findById(Long id) {
        return
            oportunidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Oportunidade with id {0} not found", id)));
    }

    @Override
    public Oportunidade update(OportunidadeDto oportunidadeDto) {
        Oportunidade existing = oportunidadeRepository.findById(oportunidadeDto.getId())
            .orElseThrow(() -> new RuntimeException(format("Oportunidade with id {0} not found", oportunidadeDto.getId())));

        BeanUtils.copyProperties(oportunidadeDto, existing);
        return oportunidadeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        oportunidadeRepository.deleteById(id);
    }
}
