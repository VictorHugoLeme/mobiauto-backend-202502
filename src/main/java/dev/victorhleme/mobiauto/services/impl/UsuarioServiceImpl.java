package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.UsuarioMapper;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.repositories.specifications.UsuarioSpecifications;
import dev.victorhleme.mobiauto.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioSpecifications usuarioSpecifications;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario save(UsuarioDto usuarioDto) {
        Usuario newUsuario = usuarioMapper.from(usuarioDto);
        newUsuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        return usuarioRepository.save(newUsuario);
    }

    @Override
    public Page<Usuario> getAll(UsuarioFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return usuarioRepository.findAll(usuarioSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Usuario with id {0} not found", id)));
    }

    @Override
    public Usuario update(UsuarioDto usuarioDto) {
        Usuario existing = usuarioRepository.findById(usuarioDto.getId())
            .orElseThrow(() -> new RuntimeException(format("Usuario with id {0} not found", usuarioDto.getId())));

        BeanUtils.copyProperties(usuarioDto, existing);
        existing.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        return usuarioRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
