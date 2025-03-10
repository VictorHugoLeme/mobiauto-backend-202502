package dev.victorhleme.mobiauto.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtSecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public JwtSecurityFilter(
        TokenService tokenService,
        UserDetailsService userDetailsService
    ) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String token = tokenRecover(request);
            if (token != null) {
                String tokenSubject = tokenService.getSubject(token);
                UserDetails user = userDetailsService.loadUserByUsername(tokenSubject);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }

    private String tokenRecover(HttpServletRequest request) {
        var authorizationHeader = request.getHeader(SecurityConstants.JWT_TOKEN_HEADER);
        if (authorizationHeader != null) {
            return authorizationHeader.replace(SecurityConstants.JWT_TOKEN_PREFIX, "");
        }
        return null;
    }

}

