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
public class OportunidadeDto {
    private Long id;
    private String clienteNome;
    private String clienteEmail;
    private String clienteTelefone;
    private String veiculoMarca;
    private String veiculoModelo;
    private String veiculoVersao;
    private String veiculoAno;
    private StatusOportunidade status = StatusOportunidade.NOVO;
    private Usuario responsavel;
    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataConclusao;
    private String motivoConclusao;
    private Revenda revenda;
}
