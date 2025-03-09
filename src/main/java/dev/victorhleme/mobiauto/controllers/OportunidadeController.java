package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.OportunidadeDto;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.services.OportunidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/v1/opotunidade")
@RequiredArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    @PostMapping
    public ResponseEntity<OportunidadeDto> save(OportunidadeDto oportunidadeDto) {
        log.debug("Creating oportunidade");
        return ResponseEntity.ok(oportunidadeService.save(oportunidadeDto));
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> getById(@PathVariable final Long id) {
        log.debug("Getting oportunidade by id: {}", id);
        final OportunidadeDto oportunidadeDto = oportunidadeService.findById(id);
        return ResponseEntity.ok(oportunidadeDto);
    }

    @GetMapping
    public ResponseEntity<Page<OportunidadeDto>> findAllPaginated(OportunidadeFilter filter) {
        log.debug("Get oportunidades by filter");
        final Page<OportunidadeDto> oportunidadeDto = oportunidadeService.getAll(filter);
        return ResponseEntity.ok(oportunidadeDto);
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody OportunidadeDto dto
    ) {
        log.debug("Updating oportunidade with id: {}", id);
        return ResponseEntity.ok(oportunidadeService.update(dto));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting oportunidade with id: {}", id);
        oportunidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
