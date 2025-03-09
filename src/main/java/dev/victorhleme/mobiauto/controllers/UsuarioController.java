package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.usuario.UsuarioCreationDto;
import dev.victorhleme.mobiauto.dtos.usuario.UsuarioDto;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.mappers.UsuarioMapper;
import dev.victorhleme.mobiauto.services.UsuarioService;
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
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UsuarioCreationDto usuarioDto) {
        log.debug("Creating usuario");
        Usuario usuario = usuarioService.save(usuarioDto);
        final URI uri = getUri(usuario.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable final Long id) {
        log.debug("Getting usuario by id: {}", id);
        return ResponseEntity.ok(usuarioMapper.from(usuarioService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> findAllPaginated(UsuarioFilter filter) {
        log.debug("Get usuarios by filter");
        return ResponseEntity.ok(usuarioService.getAll(filter).map(usuarioMapper::from));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<UsuarioDto> update(
        @PathVariable("id") Long id,
        @RequestBody UsuarioDto dto
    ) {
        log.debug("Updating usuario with id: {}", id);
        return ResponseEntity.ok(usuarioMapper.from(usuarioService.update(dto)));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting usuario with id: {}", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
