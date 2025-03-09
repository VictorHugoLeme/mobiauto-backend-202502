package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeCreationDto;
import dev.victorhleme.mobiauto.dtos.oportunidade.OportunidadeDto;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
import dev.victorhleme.mobiauto.services.OportunidadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static dev.victorhleme.mobiauto.utils.ApplicationUtils.getUri;

@Slf4j
@RestController()
@RequestMapping("/v1/opotunidade")
@RequiredArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;
    private final OportunidadeMapper oportunidadeMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OportunidadeCreationDto oportunidadeDto) {
        log.debug("Creating oportunidade");
        Oportunidade oportunidade = oportunidadeService.save(oportunidadeDto);
        final URI uri = getUri(oportunidade.getId());
        return ResponseEntity.created(uri).build();
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
        @RequestBody OportunidadeDto dto
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
