package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import org.springframework.data.domain.Page;

public interface UsuarioService {
    public Usuario save(UsuarioCreationDto usuarioDto);

    public Page<Usuario> findAll(UsuarioFilter filter);

    public Usuario findById(Long id);

    public Usuario update(UsuarioDto usuarioDto);

    public void delete(Long id);

}
