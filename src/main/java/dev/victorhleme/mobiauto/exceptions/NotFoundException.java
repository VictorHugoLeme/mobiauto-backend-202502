package dev.victorhleme.mobiauto.exceptions;

import java.io.Serial;
import java.text.MessageFormat;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3594670146757863321L;

    public NotFoundException(Class<?> clazz, Object id) {
        super(MessageFormat.format("{0} with identifier {1} not found", clazz.getSimpleName(), id.toString()));
    }
}
