package dev.victorhleme.mobiauto.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static dev.victorhleme.mobiauto.security.SecurityConstants.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService extends TokenService {


    private static final String tokenSeparator = "::";

    @Value("${app.jwt-expiry-minutes}")
    private Integer jwtExpiryMinutes;

    public String generateToken(UserDetails user) {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC512(tokenKey);
            token = JWT.create()
                .withHeader(Collections.singletonMap("typ", TOKEN_TYPE))
                .withIssuer(TOKEN_ISSUER)
                .withAudience(TOKEN_AUDIENCE)
                .withSubject(user.getUsername())
                .withExpiresAt(expirationDate())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token: ", e);
        }
        return token;
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusMinutes(jwtExpiryMinutes).toInstant(ZoneOffset.UTC);
    }

    public String generateEmailToken(Usuario usuario) {
        return getMD5(usuario.getEmail() + tokenSeparator + usuario.getId());
    }

    public String generateResetToken(Usuario Usuario) {
        long currentTimeMillis = System.currentTimeMillis();
        return getMD5(Usuario.getEmail() + tokenSeparator + currentTimeMillis);
    }

    private String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
