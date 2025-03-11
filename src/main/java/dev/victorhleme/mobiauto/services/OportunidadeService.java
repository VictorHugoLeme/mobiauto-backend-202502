package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeFinishDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeTransferDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeUpdateDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import org.springframework.data.domain.Page;

public interface OportunidadeService {
    Oportunidade save(OportunidadeCreationDto oportunidadeDto);

    Page<Oportunidade> findAll(OportunidadeFilter filter);

    Oportunidade findById(Long id);

    Oportunidade transfer(OportunidadeTransferDto transferDto);

    Oportunidade finish(OportunidadeFinishDto finishDto);

    Oportunidade update(OportunidadeUpdateDto oportunidadeDto);

    void delete(Long id);

}
