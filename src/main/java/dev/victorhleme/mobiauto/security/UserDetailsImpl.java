package dev.victorhleme.mobiauto.security;


import dev.victorhleme.mobiauto.entities.Revenda;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDetailsImpl(
    Long id,
    String username,
    String password,
    Revenda revenda,
    Set<String> authorities) implements org.springframework.security.core.userdetails.UserDetails {

    @Override
    public Collection<GrantedAuthorityImpl> getAuthorities() {
        return authorities.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password();
    }

    @Override
    public String getUsername() {
        return username();
    }

    public record GrantedAuthorityImpl(String name) implements GrantedAuthority {

        @Override
        public String getAuthority() {
            return name();
        }
    }

    public Revenda getRevenda() {
        return revenda();
    }

}
