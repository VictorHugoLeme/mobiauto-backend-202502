package dev.victorhleme.mobiauto.dtos.usuario;

import dev.victorhleme.mobiauto.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreationDto {
    private String nome;
    private String email;
    private Long revendaId;
    private Cargo cargo;
    private Boolean generatePassword;
}
