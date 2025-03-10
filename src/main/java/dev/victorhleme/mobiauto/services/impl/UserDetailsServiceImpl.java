package dev.victorhleme.mobiauto.services.impl;


import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.Cargo;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(() -> new NotFoundException(Usuario.class, username));

        if (usuario.getCargo() == null)
            log.warn("Usuario with email {} has no role ", username);

        Set<String> authorities;

        if (!usuario.getCargo().equals(Cargo.ADMINISTRADOR))
            authorities = Set.of(format("{0}-{1}", usuario.getCargo().name(), usuario.getRevenda().getId()));
        else
            authorities = Set.of(usuario.getCargo().name());

        return new UserDetailsImpl(usuario.getId(), username, usuario.getSenha(), authorities);
    }
}
