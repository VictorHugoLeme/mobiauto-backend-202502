package dev.victorhleme.mobiauto.services;


import dev.victorhleme.mobiauto.dtos.auth.*;

public interface AuthenticationService {
    LoginResponseDto login(LoginDto loginDto);

    String recoverPassword(EmailDto emailDto);

    LoginResponseDto resetPassword(PasswordResetDto passwordResetDto);

    LoginResponseDto changePassword(PasswordChangeDto passwordChangeDto);
}
