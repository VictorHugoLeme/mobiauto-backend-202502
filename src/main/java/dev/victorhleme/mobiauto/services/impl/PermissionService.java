package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.exceptions.ForbiddenException;
import dev.victorhleme.mobiauto.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PermissionService {

    public void checkAuthority(String... authority) {
        Authentication authentication = getAuthentication();
        if (isAdmin(authentication)) return;
        if (hasAuthority(authentication, authority)) return;
        throw new ForbiddenException();
    }

    public void checkForAdmin() {
        if (isAdmin(getAuthentication())) return;
        throw new ForbiddenException();
    }

    public void checkCanEditOportunidade(Oportunidade oportunidade) {
        Authentication authentication = getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        if (details.id().equals(oportunidade.getResponsavel().getId())) return;
        if (hasAuthority(authentication, "PERM_OPORTUNIDADE_MANAGE_" + oportunidade.getRevenda().getId())) return;
        throw new ForbiddenException();
    }

    private Boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(auth -> auth.equals("ROLE_ADMINISTRADOR"));
    }

    private Boolean hasAuthority(Authentication authentication, String... authorities) {
        List<String> authorityList = Arrays.asList(authorities);
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(authorityList::contains);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
