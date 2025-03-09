package dev.victorhleme.mobiauto.dtos.oportunidade;

import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.StatusOportunidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class OportunidadeCreationDto {
    private String clienteNome;
    private String clienteEmail;
    private String clienteTelefone;
    private String veiculoMarca;
    private String veiculoModelo;
    private String veiculoVersao;
    private String veiculoAno;
    private StatusOportunidade status = StatusOportunidade.NOVO;
    private Long responsavelId;
    private Long revendaId;
}
