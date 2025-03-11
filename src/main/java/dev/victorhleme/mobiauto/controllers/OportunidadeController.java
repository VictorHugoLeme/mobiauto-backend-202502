package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.oportunidade.*;
import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.mappers.OportunidadeMapper;
import dev.victorhleme.mobiauto.services.OportunidadeService;
import dev.victorhleme.mobiauto.services.impl.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static dev.victorhleme.mobiauto.utils.ApplicationUtils.getUri;

@Slf4j
@RestController()
@RequestMapping("/v1/oportunidade")
@RequiredArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;
    private final OportunidadeMapper oportunidadeMapper;
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OportunidadeCreationDto oportunidadeDto) {
        log.debug("Creating oportunidade");
        Oportunidade oportunidade = oportunidadeService.save(oportunidadeDto);
        final URI uri = getUri(oportunidade.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> getById(@PathVariable final Long id) {
        log.debug("Finding oportunidade by id: {}", id);

        Oportunidade oportunidade = oportunidadeService.findById(id);
        Long revendaId = oportunidade.getRevenda().getId();

        permissionService.checkAuthority(
            "PERM_OPORTUNIDADE_MANAGE_" + revendaId,
            "PERM_OPORTUNIDADE_EDIT_" + revendaId);

        return ResponseEntity.ok(oportunidadeMapper.from(oportunidade));
    }

    @GetMapping
    public ResponseEntity<Page<OportunidadeDto>> findAllPaginated(OportunidadeFilter filter) {
        log.debug("Finding oportunidades by filter");

        permissionService.checkAuthority(
            "PERM_OPORTUNIDADE_MANAGE_" + filter.getRevendaId(),
            "PERM_OPORTUNIDADE_EDIT_" + filter.getRevendaId());

        return ResponseEntity.ok(oportunidadeService.findAll(filter).map(oportunidadeMapper::from));
    }

    @PutMapping("/transfer/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> transfer(
        @PathVariable("id") Long id,
        @RequestBody OportunidadeTransferDto dto
    ) {
        log.debug("Transferring oportunidade with id: {} to responsavel: {}", id, dto.getNewResponsavelId());

        permissionService.checkAuthority(
            "PERM_OPORTUNIDADE_MANAGE_" + getRevendaId(dto.getId()));

        return ResponseEntity.ok(oportunidadeMapper.from(oportunidadeService.transfer(dto)));
    }

    @PutMapping("/finish/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDto> finish(
        @PathVariable("id") Long id,
        @RequestBody OportunidadeFinishDto dto
    ) {
        log.debug("Finishing oportunidade with id: {}, reason: {}", id, dto.getMotivoConclusao());
        permissionService.checkCanEditOportunidade(oportunidadeService.findById(id));
        return ResponseEntity.ok(oportunidadeMapper.from(oportunidadeService.finish(dto)));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<OportunidadeDetailsDto> update(
        @PathVariable("id") Long id,
        @RequestBody OportunidadeUpdateDto dto
    ) {
        log.debug("Updating oportunidade with id: {}", id);
        permissionService.checkCanEditOportunidade(oportunidadeService.findById(id));
        return ResponseEntity.ok(oportunidadeMapper.detailsFrom(oportunidadeService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting oportunidade with id: {}", id);
        permissionService.checkAuthority("PERM_OPORTUNIDADE_MANAGE_" + getRevendaId(id));
        oportunidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Long getRevendaId(Long id) {
        Oportunidade oportunidade = oportunidadeService.findById(id);
        return oportunidade.getRevenda().getId();
    }
}
