package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.RevendaDto;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import dev.victorhleme.mobiauto.services.RevendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/v1/revenda")
@RequiredArgsConstructor
public class RevendaController {

    private final RevendaService revendaService;

    @PostMapping
    public ResponseEntity<RevendaDto> save(RevendaDto revendaDto) {
        log.debug("Creating revenda");
        return ResponseEntity.ok(revendaService.save(revendaDto));
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<RevendaDto> getById(@PathVariable final Long id) {
        log.debug("Getting revenda by id: {}", id);
        final RevendaDto campaignDto = revendaService.findById(id);
        return ResponseEntity.ok(campaignDto);
    }

    @GetMapping
    public ResponseEntity<Page<RevendaDto>> findAllPaginated(RevendaFilter filter) {
        log.debug("Get revendas by filter");
        final Page<RevendaDto> revendaDtos = revendaService.getAll(filter);
        return ResponseEntity.ok(revendaDtos);
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<RevendaDto> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody RevendaDto dto
    ) {
        log.debug("Updating revenda with id: {}", id);
        return ResponseEntity.ok(revendaService.update(dto));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting revenda with id: {}", id);
        revendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
