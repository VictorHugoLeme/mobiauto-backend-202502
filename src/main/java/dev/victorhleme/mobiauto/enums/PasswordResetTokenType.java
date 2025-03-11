package dev.victorhleme.mobiauto.enums;

import lombok.Getter;
import lombok.Setter;

public enum PasswordResetTokenType {

    PASSWORD_RECOVERY("MobiAuto password recovery"),
    FIRST_ACCESS("Welcome to MobiAuto");

    @Setter
    @Getter
    private Integer expiryMinutes;
    public String emailSubject;

    PasswordResetTokenType(String emailSubject) {
        this.emailSubject = emailSubject;
    }

}
