package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.UsuarioMapper;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.repositories.specifications.UsuarioSpecifications;
import dev.victorhleme.mobiauto.security.PasswordGenerator;
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

import java.util.List;

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

    @Override
    public Usuario save(UsuarioCreationDto usuarioDto) {
        Usuario newUsuario = usuarioMapper.from(usuarioDto);

        newUsuario.setSenha(passwordEncoder.encode(generatePassword()));

        if (usuarioDto.getRevendaId() != null)
            newUsuario.setRevenda(revendaService.findById(usuarioDto.getRevendaId()));

        newUsuario = usuarioRepository.save(newUsuario);
        emailService.sendEmail(newUsuario, FIRST_ACCESS);
        return newUsuario;
    }

    @Override
    public Page<Usuario> findAllPageable(UsuarioFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return usuarioRepository.findAll(usuarioSpecifications.getSpecification(filter), pageable);
    }

    @Override
    public List<Usuario> findAll(UsuarioFilter filter) {
        return usuarioRepository.findAll(usuarioSpecifications.getSpecification(filter));
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Usuario.class, id));
    }

    @Override
    public Usuario update(UsuarioDto usuarioDto) {
        Usuario existing = findById(usuarioDto.getId());
        BeanUtils.copyProperties(usuarioDto, existing);
        return usuarioRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Long getRevendaIdFromUser(Long id) {
        return findById(id).getRevenda().getId();
    }

    private String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
            .useDigits(true).useSpecialCharacter(true)
            .useLower(true).useUpper(true).build();
        return passwordGenerator.generate(12);
    }
}
