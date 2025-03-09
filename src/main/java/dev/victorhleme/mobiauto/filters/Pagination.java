package dev.victorhleme.mobiauto.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int page = 0;
    private int size = 10;
}
