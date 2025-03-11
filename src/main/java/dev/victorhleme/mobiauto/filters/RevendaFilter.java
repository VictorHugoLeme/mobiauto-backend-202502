package dev.victorhleme.mobiauto.filters;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@With
@Data
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RevendaFilter extends Pagination {
    @Parameter(description = "CNPJ of the searched Revenda")
    private String cnpj;
    @Parameter(description = "Nome Social of the searched Revenda")
    private String nomeSocial;
}
