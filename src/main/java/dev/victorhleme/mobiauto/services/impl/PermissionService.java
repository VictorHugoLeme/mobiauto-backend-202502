package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.exceptions.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public void checkAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAdmin(authentication)) return;
        if (hasAuthority(authentication, authority)) return;
        throw new ForbiddenException();
    }

    private Boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(auth -> auth.equals("ROLE_ADMINISTRADOR"));
    }

    private Boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(auth -> auth.equals(authority));
    }
}
