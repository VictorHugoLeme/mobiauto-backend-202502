package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevendaMapper {

    public RevendaDto from(final Revenda entity) {
        final RevendaDto dto = new RevendaDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Revenda from(final RevendaDto entity) {
        final Revenda dto = new Revenda();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
