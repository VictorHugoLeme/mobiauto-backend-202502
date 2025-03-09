package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDto;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
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
    private final OportunidadeMapper oportunidadeMapper;

    @PostMapping
    public ResponseEntity<OportunidadeDto> save(OportunidadeCreationDto oportunidadeDto) {
        log.debug("Creating oportunidade");
        return ResponseEntity.ok(oportunidadeMapper.from(oportunidadeService.save(oportunidadeDto)));
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> getById(@PathVariable final Long id) {
        log.debug("Getting oportunidade by id: {}", id);
        return ResponseEntity.ok(oportunidadeMapper.from(oportunidadeService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<OportunidadeDto>> findAllPaginated(OportunidadeFilter filter) {
        log.debug("Get oportunidades by filter");
        return ResponseEntity.ok(oportunidadeService.getAll(filter).map(oportunidadeMapper::from));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody OportunidadeDto dto
    ) {
        log.debug("Updating oportunidade with id: {}", id);
        return ResponseEntity.ok(oportunidadeMapper.from(oportunidadeService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting oportunidade with id: {}", id);
        oportunidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
