package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.auth.*;
import dev.victorhleme.mobiauto.services.AuthenticationService;
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
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        log.debug("Usuario with email {} logging in", loginDto.getLogin());
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<?> recoverPassword(@RequestBody @Valid EmailDto emailDto) {
        log.debug("Usuario with email {} recovering password", emailDto.getEmail());
        authenticationService.recoverPassword(emailDto);
        return ResponseEntity.ok("Recovery email sent");
    }

    @PostMapping("/first-access")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        log.debug("Usuario with token {} resetting password", passwordResetDto.getToken());
        LoginResponseDto loginResponseDto = authenticationService.resetPassword(passwordResetDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeDto passwordChangeDto) {
        log.debug("Usuario with e-mail {} changing password", passwordChangeDto.getEmail());
        LoginResponseDto loginResponseDto = authenticationService.changePassword(passwordChangeDto);
        return ResponseEntity.ok(loginResponseDto);
    }

}
