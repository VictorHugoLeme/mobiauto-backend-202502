package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsuarioService {
    Usuario save(UsuarioCreationDto usuarioDto);

    Page<Usuario> findAllPageable(UsuarioFilter filter);

    List<Usuario> findAll(UsuarioFilter filter);

    Usuario findById(Long id);

    Usuario update(UsuarioDto usuarioDto);

    void delete(Long id);

    Long getRevendaIdFromUser(Long id);

}
