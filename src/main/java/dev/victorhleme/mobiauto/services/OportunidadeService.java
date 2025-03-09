package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.OportunidadeDto;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import org.springframework.data.domain.Page;

public interface OportunidadeService {
    public OportunidadeDto save(OportunidadeDto oportunidadeDto);

    public Page<OportunidadeDto> getAll(OportunidadeFilter filter);
    public OportunidadeDto findById(Long id);

    public OportunidadeDto update(OportunidadeDto oportunidadeDto);

    public void delete(Long id);

}
