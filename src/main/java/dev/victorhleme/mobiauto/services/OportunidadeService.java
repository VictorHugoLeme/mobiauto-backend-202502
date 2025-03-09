package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import org.springframework.data.domain.Page;

public interface OportunidadeService {
    public Oportunidade save(OportunidadeCreationDto oportunidadeDto);

    public Page<Oportunidade> getAll(OportunidadeFilter filter);
    public Oportunidade findById(Long id);

    public Oportunidade update(OportunidadeDto oportunidadeDto);

    public void delete(Long id);

}
