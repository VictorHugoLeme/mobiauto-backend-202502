package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.UsuarioMapper;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.repositories.specifications.UsuarioSpecifications;
import dev.victorhleme.mobiauto.services.EmailService;
import dev.victorhleme.mobiauto.services.RevendaService;
import dev.victorhleme.mobiauto.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.victorhleme.mobiauto.enums.PasswordResetTokenType.FIRST_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioSpecifications usuarioSpecifications;
    private final RevendaService revendaService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PermissionService permissionService;

    @Override
    public Usuario save(UsuarioCreationDto usuarioDto) {
        permissionService.checkAuthority("PERM_USUARIO_CREATE_" + usuarioDto.getRevendaId());
        Usuario newUsuario = usuarioMapper.from(usuarioDto);

        newUsuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));

        if (usuarioDto.getRevendaId() != null)
            newUsuario.setRevenda(revendaService.findById(usuarioDto.getRevendaId()));

        newUsuario = usuarioRepository.save(newUsuario);
        emailService.sendEmail(newUsuario, FIRST_ACCESS);
        return newUsuario;
    }

    @Override
    public Page<Usuario> getAll(UsuarioFilter filter) {
        permissionService.checkAuthority("PERM_USUARIO_READ_" + filter.getRevendaId());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return usuarioRepository.findAll(usuarioSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public Usuario findById(Long id) {
        permissionService.checkAuthority("PERM_USUARIO_READ_" + getRevendaIdFromUser(id));
        return findUsuarioOrThrowException(id);
    }

    @Override
    public Usuario update(UsuarioDto usuarioDto) {
        Usuario existing = findUsuarioOrThrowException(usuarioDto.getId());
        permissionService.checkAuthority("PERM_USUARIO_MANAGE_" + existing.getRevenda().getId());
        BeanUtils.copyProperties(usuarioDto, existing);
        return usuarioRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        permissionService.checkAuthority("PERM_USUARIO_MANAGE_" + getRevendaIdFromUser(id));
        usuarioRepository.deleteById(id);
    }

    private Usuario findUsuarioOrThrowException(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Usuario.class, id));
    }

    private Long getRevendaIdFromUser(Long id) {
        return findUsuarioOrThrowException(id).getRevenda().getId();
    }
}
