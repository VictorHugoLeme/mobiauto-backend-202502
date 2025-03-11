package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDetailsDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevendaMapper {

    private final OportunidadeMapper oportunidadeMapper;

    public RevendaDto from(final Revenda entity) {
        final RevendaDto dto = new RevendaDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Revenda from(final RevendaDto dto) {
        final Revenda entity = new Revenda();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public Revenda from(final RevendaCreationDto dto) {
        final Revenda entity = new Revenda();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public RevendaDetailsDto detailsFrom(final Revenda entity) {
        final RevendaDetailsDto dto = new RevendaDetailsDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setOportunidadeAmount(entity.getOportunidades().size());
        return dto;
    }

}
