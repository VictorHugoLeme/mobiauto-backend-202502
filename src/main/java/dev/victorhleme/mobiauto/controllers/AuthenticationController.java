package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.auth.*;
import dev.victorhleme.mobiauto.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login to the application")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        log.debug("Usuario with email {} logging in", loginDto.getLogin());
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping("/recover-password")
    @Operation(summary = "Recover the password")
    public ResponseEntity<?> recoverPassword(@RequestBody @Valid EmailDto emailDto) {
        log.debug("Usuario with email {} recovering password", emailDto.getEmail());
        return ResponseEntity.ok(authenticationService.recoverPassword(emailDto));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset the password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        log.debug("Usuario with token {} resetting password", passwordResetDto.getToken());
        LoginResponseDto loginResponseDto = authenticationService.resetPassword(passwordResetDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change the password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeDto passwordChangeDto) {
        log.debug("Usuario with e-mail {} changing password", passwordChangeDto.getEmail());
        LoginResponseDto loginResponseDto = authenticationService.changePassword(passwordChangeDto);
        return ResponseEntity.ok(loginResponseDto);
    }

}
