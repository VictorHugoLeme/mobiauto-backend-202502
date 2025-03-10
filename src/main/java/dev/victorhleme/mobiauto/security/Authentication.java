package dev.victorhleme.mobiauto.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Authentication {

    public UserDetailsImpl getLoggedUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getLoggedUserName() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(a -> ((UserDetailsImpl) a.getPrincipal()).getUsername())
            .orElse(SecurityConstants.ANONYMOUS_USER);
    }

}
