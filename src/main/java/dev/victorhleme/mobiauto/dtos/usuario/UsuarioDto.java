package dev.victorhleme.mobiauto.dtos.usuario;

import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private Revenda revenda;
    private Cargo cargo;

    // For practical reasons
    // This would preferably be sent via e-mail
    private String token;
}
