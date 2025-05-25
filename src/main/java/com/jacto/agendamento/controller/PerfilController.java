package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.PerfilDTO;
import com.jacto.agendamento.entity.Perfil;
import com.jacto.agendamento.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfis")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public List<PerfilDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PerfilDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Perfil> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerfilDTO> salvar(@Valid @RequestBody PerfilDTO dto) {
        Perfil entity = convertToEntity(dto);
        Perfil salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PerfilDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody PerfilDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(dto.getDescricao());
                Perfil atualizado = service.salvar(c);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
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
    private Perfil convertToEntity(PerfilDTO dto) {
        Perfil entity = new Perfil();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}