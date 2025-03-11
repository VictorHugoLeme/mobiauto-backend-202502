package dev.victorhleme.mobiauto.dtos.oportunidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class OportunidadeUpdateDto {
    private Long id;
    private String clienteNome;
    private String clienteEmail;
    private String clienteTelefone;
    private String veiculoMarca;
    private String veiculoModelo;
    private String veiculoVersao;
    private String veiculoAno;
}
