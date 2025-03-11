package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDetailsDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import dev.victorhleme.mobiauto.mappers.RevendaMapper;
import dev.victorhleme.mobiauto.services.RevendaService;
import dev.victorhleme.mobiauto.services.impl.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Revenda Controller")
@RequiredArgsConstructor
public class RevendaController {

    private final RevendaService revendaService;
    private final RevendaMapper revendaMapper;
    private final PermissionService permissionService;

    @PostMapping
    @Operation(summary = "Create a Revenda")
    public ResponseEntity<?> save(@RequestBody RevendaCreationDto revendaDto) {
        log.debug("Creating revenda");
        permissionService.checkForAdmin();
        Revenda revenda = revendaService.save(revendaDto);
        final URI uri = getUri(revenda.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id:[0-9]+}")
    @Operation(summary = "Find a Revenda by id")
    public ResponseEntity<RevendaDetailsDto> findById(@PathVariable final Long id) {
        log.debug("Finding revenda by id: {}", id);
        permissionService.checkForAdmin();
        return ResponseEntity.ok(revendaMapper.detailsFrom(revendaService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "List Revendas fintered and paginated")
    public ResponseEntity<Page<RevendaDetailsDto>> findAllPaginated(RevendaFilter filter) {
        log.debug("Finding revendas by filter");
        permissionService.checkForAdmin();
        return ResponseEntity.ok(revendaService.findAll(filter).map(revendaMapper::detailsFrom));
    }

    @PutMapping("/{id:[0-9]+}")
    @Operation(summary = "Update a Revenda")
    public ResponseEntity<RevendaDetailsDto> update(
        @PathVariable("id") Long id,
        @RequestBody RevendaDto dto
    ) {
        log.debug("Updating revenda with id: {}", id);
        permissionService.checkForAdmin();
        return ResponseEntity.ok(revendaMapper.detailsFrom(revendaService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    @Operation(summary = "Delete a Revenda")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting revenda with id: {}", id);
        permissionService.checkForAdmin();
        revendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
