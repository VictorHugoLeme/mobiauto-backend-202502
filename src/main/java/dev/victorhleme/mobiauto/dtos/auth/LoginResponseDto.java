package dev.victorhleme.mobiauto.dtos.auth;

import java.util.Set;

public record LoginResponseDto(
    String token,
    String email,
    String name,
    Set<String> authorities
) {
}
