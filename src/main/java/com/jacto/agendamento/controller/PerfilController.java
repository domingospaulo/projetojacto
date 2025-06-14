package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.PerfilDTO;
import com.jacto.agendamento.controller.requests.PerfilRequest;
import com.jacto.agendamento.entity.Perfil;
import com.jacto.agendamento.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfis")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<PerfilDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PerfilDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Perfil> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PerfilDTO> salvar(@Valid @RequestBody PerfilRequest request) {
        Perfil entity = convertToEntity(request);
        Perfil salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PerfilDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody PerfilRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(request.getDescricao());
                Perfil atualizado = service.salvar(c);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Integer codigo) {
        if (service.buscarPorCodigo(codigo).isPresent()) {
            service.deletar(codigo);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Converter entidade para DTO
    private PerfilDTO convertToDto(Perfil entity) {
        return new PerfilDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converter DTO para entidade
    private Perfil convertToEntity(PerfilRequest request) {
        Perfil entity = new Perfil();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }

}