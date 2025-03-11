package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import org.springframework.data.domain.Page;

public interface RevendaService {
    public Revenda save(RevendaCreationDto revendaDto);

    public Page<Revenda> findAll(RevendaFilter filter);

    public Revenda findById(Long id);

    public Revenda update(RevendaDto revendaDto);

    public void delete(Long id);

}
