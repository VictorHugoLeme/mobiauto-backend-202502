package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.RevendaDto;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import org.springframework.data.domain.Page;

public interface RevendaService {
    public RevendaDto save(RevendaDto revendaDto);

    public Page<RevendaDto> getAll(RevendaFilter filter);
    public RevendaDto findById(Long id);

    public RevendaDto update(RevendaDto revendaDto);

    public void delete(Long id);

}
