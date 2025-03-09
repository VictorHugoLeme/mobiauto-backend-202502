package dev.victorhleme.mobiauto.dtos;

import dev.victorhleme.mobiauto.entities.Oportunidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class RevendaDto {
    private Long id;
    private String cnpj;
    private String nomeSocial;
    private List<Oportunidade> oportunidades;
}
