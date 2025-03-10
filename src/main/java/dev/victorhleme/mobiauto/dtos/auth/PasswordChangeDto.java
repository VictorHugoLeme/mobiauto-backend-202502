package dev.victorhleme.mobiauto.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordChangeDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;
}
