package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import dev.victorhleme.mobiauto.mappers.RevendaMapper;
import dev.victorhleme.mobiauto.services.RevendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static dev.victorhleme.mobiauto.utils.ApplicationUtils.getUri;

@Slf4j
@RestController()
@RequestMapping("/v1/revenda")
@RequiredArgsConstructor
public class RevendaController {

    private final RevendaService revendaService;
    private final RevendaMapper revendaMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RevendaCreationDto revendaDto) {
        log.debug("Creating revenda");
        Revenda revenda = revendaService.save(revendaDto);
        final URI uri = getUri(revenda.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<RevendaDto> findById(@PathVariable final Long id) {
        log.debug("Finding revenda by id: {}", id);
        return ResponseEntity.ok(revendaMapper.from(revendaService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<RevendaDto>> findAllPaginated(RevendaFilter filter) {
        log.debug("Finding revendas by filter");
        return ResponseEntity.ok(revendaService.findAll(filter).map(revendaMapper::from));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<RevendaDto> update(
        @PathVariable("id") Long id,
        @RequestBody RevendaDto dto
    ) {
        log.debug("Updating revenda with id: {}", id);
        return ResponseEntity.ok(revendaMapper.from(revendaService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting revenda with id: {}", id);
        revendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
