package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    public UsuarioDto from(final Usuario entity) {
        final UsuarioDto dto = new UsuarioDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Usuario from(final UsuarioDto entity) {
        final Usuario dto = new Usuario();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
