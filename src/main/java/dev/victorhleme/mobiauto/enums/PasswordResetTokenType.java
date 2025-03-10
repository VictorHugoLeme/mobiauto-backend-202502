package dev.victorhleme.mobiauto.enums;

import lombok.Getter;
import lombok.Setter;

public enum PasswordResetTokenType {

    PASSWORD_RECOVERY("MobiAuto password recovery", "reset-password"),
    FIRST_ACCESS("Welcome to MobiAuto", "first-access");

    @Setter
    @Getter
    private Integer expiryMinutes;
    public String emailSubject;
    public String operation;

    PasswordResetTokenType(String emailSubject, String operation) {
        this.emailSubject = emailSubject;
        this.operation = operation;
    }

}
