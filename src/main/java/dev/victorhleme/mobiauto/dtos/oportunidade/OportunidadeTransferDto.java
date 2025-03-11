package dev.victorhleme.mobiauto.dtos.oportunidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class OportunidadeTransferDto {
    private Long id;
    private Long newResponsavelId;
}
