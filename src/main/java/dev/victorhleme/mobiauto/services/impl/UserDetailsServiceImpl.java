package dev.victorhleme.mobiauto.services.impl;


import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

        Set<String> authorities = getAuthoritiesForUser(usuario);

        return new UserDetailsImpl(usuario.getId(), username, usuario.getSenha(), usuario.getRevenda(), authorities);
    }

    private Set<String> getAuthoritiesForUser(Usuario usuario) {
        Set<String> authorities = new HashSet<>();

        authorities.add("ROLE_" + usuario.getCargo().name());

        switch (usuario.getCargo()) {
            case ADMINISTRADOR:
                break;

            case PROPRIETARIO:
                authorities.add("PERM_USUARIO_CREATE_" + usuario.getRevenda().getId());
                authorities.add("PERM_USUARIO_MANAGE_" + usuario.getRevenda().getId());
                authorities.add("PERM_USUARIO_READ_" + usuario.getRevenda().getId());
                authorities.add("PERM_OPORTUNIDADE_MANAGE_" + usuario.getRevenda().getId());
                authorities.add("PERM_OPORTUNIDADE_TRANSFER_" + usuario.getRevenda().getId());
                break;

            case GERENTE:
                authorities.add("PERM_USUARIO_CREATE_" + usuario.getRevenda().getId());
                authorities.add("PERM_USUARIO_READ_" + usuario.getRevenda().getId());
                authorities.add("PERM_OPORTUNIDADE_MANAGE_" + usuario.getRevenda().getId());
                authorities.add("PERM_OPORTUNIDADE_TRANSFER_" + usuario.getRevenda().getId());
                break;

            case ASSISTENTE:
                authorities.add("PERM_OPORTUNIDADE_EDIT_" + usuario.getId());
                break;

            default:
                log.warn("No specific permissions defined for role {}", usuario.getCargo());
        }

        return authorities;
    }

}
