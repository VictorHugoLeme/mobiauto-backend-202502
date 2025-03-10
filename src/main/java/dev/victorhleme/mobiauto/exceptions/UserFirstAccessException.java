package dev.victorhleme.mobiauto.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.text.MessageFormat;

public class UserFirstAccessException extends AuthenticationException {
    public UserFirstAccessException(Object id) {
        super(MessageFormat.format("This is the first access for user with identifier ({0}). The password must be reset via e-mail first.", id.toString()));
    }
}
