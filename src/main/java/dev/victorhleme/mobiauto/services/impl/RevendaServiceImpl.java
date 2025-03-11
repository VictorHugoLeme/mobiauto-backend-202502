package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import dev.victorhleme.mobiauto.mappers.RevendaMapper;
import dev.victorhleme.mobiauto.repositories.RevendaRepository;
import dev.victorhleme.mobiauto.repositories.specifications.RevendaSpecifications;
import dev.victorhleme.mobiauto.services.RevendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RevendaServiceImpl implements RevendaService {

    private final RevendaMapper revendaMapper;
    private final RevendaRepository revendaRepository;
    private final RevendaSpecifications revendaSpecifications;

    @Override
    public Revenda save(RevendaCreationDto revendaDto) {
        Revenda newRevenda = revendaMapper.from(revendaDto);
        return revendaRepository.save(newRevenda);
    }

    @Override
    public Page<Revenda> findAll(RevendaFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return revendaRepository.findAll(revendaSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public Revenda findById(Long id) {
        return revendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Revenda.class, id));
    }

    @Override
    public Revenda update(RevendaDto revendaDto) {
        Revenda existing = revendaRepository.findById(revendaDto.getId())
            .orElseThrow(() -> new NotFoundException(Revenda.class, revendaDto.getId()));

        BeanUtils.copyProperties(revendaDto, existing);
        return revendaRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        revendaRepository.deleteById(id);
    }
}
