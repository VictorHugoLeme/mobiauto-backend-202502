package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDetailsDto;
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

    public Oportunidade from(final OportunidadeDto dto) {
        final Oportunidade entity = new Oportunidade();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public Oportunidade from(final OportunidadeCreationDto dto) {
        final Oportunidade entity = new Oportunidade();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public OportunidadeDetailsDto detailsFrom(final Oportunidade entity) {
        final OportunidadeDetailsDto dto = new OportunidadeDetailsDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setResponsavelId(entity.getResponsavel().getId());
        dto.setRevendaId(entity.getRevenda().getId());
        return dto;
    }
}
