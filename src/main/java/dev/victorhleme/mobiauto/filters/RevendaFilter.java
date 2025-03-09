package dev.victorhleme.mobiauto.filters;

import lombok.*;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RevendaFilter extends Pagination {
    private String cnpj;
    private String nomeSocial;
}
