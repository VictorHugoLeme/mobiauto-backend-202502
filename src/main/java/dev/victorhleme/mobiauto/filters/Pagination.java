package dev.victorhleme.mobiauto.filters;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    @Parameter(description = "Page number")
    private int page = 0;
    @Parameter(description = "Page size")
    private int size = 10;
}
