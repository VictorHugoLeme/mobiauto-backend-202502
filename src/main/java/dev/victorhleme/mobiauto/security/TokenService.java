package dev.victorhleme.mobiauto.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.victorhleme.mobiauto.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${app.jwt-secret}")
    protected String tokenKey;

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(tokenKey);
            return JWT.require(algorithm)
                .withIssuer(SecurityConstants.TOKEN_ISSUER)
                .build()
                .verify(tokenJWT)
                .getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("Invalid or expired JWT token", e);
        }
    }
}
