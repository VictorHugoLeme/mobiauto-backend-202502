package dev.victorhleme.mobiauto.filters;

import dev.victorhleme.mobiauto.enums.StatusOportunidade;
import lombok.*;

import java.time.LocalDateTime;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OportunidadeFilter extends Pagination {
    private String clienteNome;
    private String clienteEmail;
    private String clienteTelefone;
    private String veiculoMarca;
    private String veiculoModelo;
    private String veiculoVersao;
    private String veiculoAno;
    private StatusOportunidade status;
    private Long usuarioId;
    private LocalDateTime conclusaoBefore;
    private LocalDateTime conclusaoAfter;
    private LocalDateTime atribuicaoBefore;
    private LocalDateTime atribuicaoAfter;
    private Long revendaId;
}
