package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OportunidadeMapper {

    public OportunidadeDto from(final Oportunidade entity) {
        final OportunidadeDto dto = new OportunidadeDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Oportunidade from(final OportunidadeDto entity) {
        final Oportunidade dto = new Oportunidade();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Oportunidade from(final OportunidadeCreationDto entity) {
        final Oportunidade dto = new Oportunidade();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
