package dev.victorhleme.mobiauto.controllers;

import dev.victorhleme.mobiauto.dtos.UsuarioDto;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> save(UsuarioDto usuarioDto) {
        log.debug("Creating usuario");
        return ResponseEntity.ok(usuarioService.save(usuarioDto));
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable final Long id) {
        log.debug("Getting usuario by id: {}", id);
        final UsuarioDto usuarioDto = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> findAllPaginated(UsuarioFilter filter) {
        log.debug("Get usuarios by filter");
        final Page<UsuarioDto> usuarioDtos = usuarioService.getAll(filter);
        return ResponseEntity.ok(usuarioDtos);
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<UsuarioDto> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody UsuarioDto dto
    ) {
        log.debug("Updating usuario with id: {}", id);
        return ResponseEntity.ok(usuarioService.update(dto));
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Deleting usuario with id: {}", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
