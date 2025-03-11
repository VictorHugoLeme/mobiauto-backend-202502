package dev.victorhleme.mobiauto.mappers;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDetailsDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
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

    public Usuario from(final UsuarioDto dto) {
        final Usuario entity = new Usuario();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public Usuario from(final UsuarioCreationDto dto) {
        final Usuario entity = new Usuario();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public UsuarioDetailsDto detailsFrom(final Usuario entity) {
        final UsuarioDetailsDto dto = new UsuarioDetailsDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
