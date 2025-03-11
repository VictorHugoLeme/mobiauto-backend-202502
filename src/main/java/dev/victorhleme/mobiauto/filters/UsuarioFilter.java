package dev.victorhleme.mobiauto.filters;

import dev.victorhleme.mobiauto.enums.Cargo;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@With
@Data
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UsuarioFilter extends Pagination {
    @Parameter(description = "Nome of the searched Usuario")
    private String nome;
    @Parameter(description = "Email of the searched Usuario")
    private String email;
    @Parameter(description = "Id of the Revenda associated to the Usuario")
    private Long revendaId;
    @Parameter(description = "Cargo of the searched Usuario")
    private Cargo cargo;
}
