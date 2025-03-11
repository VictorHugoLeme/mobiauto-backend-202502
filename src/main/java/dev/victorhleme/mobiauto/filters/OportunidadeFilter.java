package dev.victorhleme.mobiauto.filters;

import dev.victorhleme.mobiauto.enums.StatusOportunidade;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDateTime;

@With
@Data
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OportunidadeFilter extends Pagination {

    @Parameter(description = "Interested client name")
    private String clienteNome;
    @Parameter(description = "Interested client e-mail")
    private String clienteEmail;
    @Parameter(description = "Interested client phone")
    private String clienteTelefone;
    @Parameter(description = "Interest car brand")
    private String veiculoMarca;
    @Parameter(description = "Interest car model")
    private String veiculoModelo;
    @Parameter(description = "Interest car version")
    private String veiculoVersao;
    @Parameter(description = "Interest car year")
    private String veiculoAno;
    @Parameter(description = "Oportunidade Status")
    private StatusOportunidade status;
    @Parameter(description = "Responsible Usuario")
    private Long responsavelId;
    @Parameter(description = "The finish date must be before:")
    private LocalDateTime conclusaoBefore;
    @Parameter(description = "The finish date must be after:")
    private LocalDateTime conclusaoAfter;
    @Parameter(description = "The assignment date must be before:")
    private LocalDateTime atribuicaoBefore;
    @Parameter(description = "The assignment date must be after:")
    private LocalDateTime atribuicaoAfter;
    @Parameter(description = "Id of the assigned Revenda")
    private Long revendaId;
}
