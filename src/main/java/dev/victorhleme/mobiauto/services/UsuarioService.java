package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import org.springframework.data.domain.Page;

public interface UsuarioService {
    public Usuario save(UsuarioDto usuarioDto);

    public Page<Usuario> getAll(UsuarioFilter filter);
    public Usuario findById(Long id);

    public Usuario update(UsuarioDto usuarioDto);

    public void delete(Long id);

}
