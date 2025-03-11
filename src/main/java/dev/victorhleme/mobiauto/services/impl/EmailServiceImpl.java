package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.entities.PasswordResetToken;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.PasswordResetTokenType;
import dev.victorhleme.mobiauto.repositories.PasswordResetTokenRepository;
import dev.victorhleme.mobiauto.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static dev.victorhleme.mobiauto.enums.PasswordResetTokenType.FIRST_ACCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${app.host-name}")
    private String webserviceHostname;

    private final ConfigurableEnvironment env;
    private final AuthTokenService authTokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public String sendEmail(Usuario usuario, PasswordResetTokenType type) {

        String resetToken = authTokenService.generateResetToken(usuario);
        PasswordResetToken passwordResetToken = refreshOrCreate(resetToken, usuario, type);

        passwordResetTokenRepository.save(passwordResetToken);

        String operation = "reset-password";
        String subject = type.emailSubject;
        Integer expiryTime = FIRST_ACCESS.getExpiryMinutes();
        String link = MessageFormat.format("{0}/v1/auth/{1}", webserviceHostname, operation);

        // Now, we could send an e-mail, with a link for password resetting.
        // For practical reasons, I'm going to just log the link and token below.
        // Further information can be found in the documantation and Postman Collection
        log.info("Password reset url: {}", link);
        log.info("Password reset token: {}", resetToken);

        return resetToken;

    }

    private PasswordResetToken refreshOrCreate(String resetToken, Usuario usuario, PasswordResetTokenType type) {
        return passwordResetTokenRepository
            .findByUsuarioAndType(usuario, type)
            .map(existingToken -> existingToken.refreshToken(resetToken, getExpiry(type)))
            .orElse(new PasswordResetToken(usuario, resetToken, type, getExpiry(type)));
    }

    private LocalDateTime getExpiry(PasswordResetTokenType type) {
        return LocalDateTime.now().plusMinutes(type.getExpiryMinutes());
    }

}
