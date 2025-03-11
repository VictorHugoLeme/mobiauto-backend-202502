package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDetailsDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.UsuarioMapper;
import dev.victorhleme.mobiauto.services.UsuarioService;
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
@RequestMapping("/v1/usuario")
@Tag(name = "Usuario Controller")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final PermissionService permissionService;

    @PostMapping
    @Operation(summary = "Create an Usuario")
    public ResponseEntity<?> save(@RequestBody UsuarioCreationDto usuarioDto) {
        log.debug("Creating usuario");
        permissionService.checkAuthority("PERM_USUARIO_CREATE_" + usuarioDto.getRevendaId());
        Usuario usuario = usuarioService.save(usuarioDto);
        final URI uri = getUri(usuario.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id:[0-9]+}")
    @Operation(summary = "Find an Usuario by id")
    public ResponseEntity<UsuarioDetailsDto> findById(@PathVariable final Long id) {
        log.debug("Finding usuario by id: {}", id);
        permissionService.checkAuthority("PERM_USUARIO_READ_" + usuarioService.getRevendaIdFromUser(id));
        return ResponseEntity.ok(usuarioMapper.detailsFrom(usuarioService.findById(id)));
    }

    @GetMapping
    @Operation(summary = "List Usuarios filtered and paginated")
    public ResponseEntity<Page<UsuarioDetailsDto>> findAllPaginated(UsuarioFilter filter) {
        log.debug("Finding usuarios by filter");
        permissionService.checkAuthority("PERM_USUARIO_READ_" + filter.getRevendaId());
        return ResponseEntity.ok(usuarioService.findAllPageable(filter).map(usuarioMapper::detailsFrom));
    }

    @PutMapping("/{id:[0-9]+}")
    @Operation(summary = "Update an Usuario")
    public ResponseEntity<UsuarioDetailsDto> update(
        @PathVariable("id") Long id,
        @RequestBody UsuarioDto dto
    ) {
        log.debug("Updating usuario with id: {}", id);
        permissionService.checkAuthority("PERM_USUARIO_EDIT_" + usuarioService.findById(id).getRevenda().getId());
        return ResponseEntity.ok(usuarioMapper.detailsFrom(usuarioService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    @Operation(summary = "Delete an Usuario")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting usuario with id: {}", id);
        permissionService.checkAuthority("PERM_USUARIO_MANAGE_" + usuarioService.findById(id).getRevenda().getId());
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
