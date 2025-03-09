package dev.victorhleme.mobiauto.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ApplicationUtils {
    public static URI getUri(final Long id) {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id).toUri();
    }
}
