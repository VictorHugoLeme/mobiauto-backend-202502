package dev.victorhleme.mobiauto.dtos.revenda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class RevendaDetailsDto {
    private Long id;
    private String cnpj;
    private String nomeSocial;
    private Integer oportunidadeAmount;
}
