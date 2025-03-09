package dev.victorhleme.mobiauto.filters;

import dev.victorhleme.mobiauto.enums.Cargo;
import lombok.*;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UsuarioFilter extends Pagination {
    private String nome;
    private String email;
    private Long revendaId;
    private Cargo cargo;
}
