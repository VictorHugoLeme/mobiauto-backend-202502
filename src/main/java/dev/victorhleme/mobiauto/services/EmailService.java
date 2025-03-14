package dev.victorhleme.mobiauto.services;

import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.PasswordResetTokenType;

public interface EmailService {
    String sendEmail(Usuario usuario, PasswordResetTokenType type);
}
