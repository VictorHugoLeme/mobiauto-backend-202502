package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.dtos.UsuarioDto;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import org.springframework.data.domain.Page;

public interface UsuarioService {
    public UsuarioDto save(UsuarioDto usuarioDto);

    public Page<UsuarioDto> getAll(UsuarioFilter filter);
    public UsuarioDto findById(Long id);

    public UsuarioDto update(UsuarioDto usuarioDto);

    public void delete(Long id);

}
