package dev.victorhleme.mobiauto.config;

import dev.victorhleme.mobiauto.enums.PasswordResetTokenType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenConfig {

    @Value("${app.recovery-token.expiry-minutes}")
    private Integer passwordRecoveryTokenExpiryMinutes;

    @Value("${app.first-access-token.expiry-minutes}")
    private Integer firstAccessTokenExpiryMinutes;

    @PostConstruct
    public void init() {
        PasswordResetTokenType.PASSWORD_RECOVERY.setExpiryMinutes(passwordRecoveryTokenExpiryMinutes);
        PasswordResetTokenType.FIRST_ACCESS.setExpiryMinutes(firstAccessTokenExpiryMinutes);
    }

}
