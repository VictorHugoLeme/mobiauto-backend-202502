package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.OportunidadeDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
import dev.victorhleme.mobiauto.repositories.OportunidadeRepository;
import dev.victorhleme.mobiauto.repositories.specifications.OportunidadeSpecifications;
import dev.victorhleme.mobiauto.services.OportunidadeService;
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

    @Override
    public OportunidadeDto save(OportunidadeDto oportunidadeDto) {
        Oportunidade newOportunidade = oportunidadeMapper.from(oportunidadeDto);
        return oportunidadeMapper.from(oportunidadeRepository.save(newOportunidade));
    }

    @Override
    public Page<OportunidadeDto> getAll(OportunidadeFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return oportunidadeRepository.findAll(oportunidadeSpecifications.getSpecification(filter), pageable).map(oportunidadeMapper::from);
    }

    @Override
    public OportunidadeDto findById(Long id) {
        return oportunidadeMapper.from(
            oportunidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Oportunidade with id {0} not found", id)))
        );
    }

    @Override
    public OportunidadeDto update(OportunidadeDto oportunidadeDto) {
        Oportunidade existing = oportunidadeRepository.findById(oportunidadeDto.getId())
            .orElseThrow(() -> new RuntimeException(format("Oportunidade with id {0} not found", oportunidadeDto.getId())));

        BeanUtils.copyProperties(oportunidadeDto, existing);
        return oportunidadeMapper.from(oportunidadeRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        oportunidadeRepository.deleteById(id);
    }
}
