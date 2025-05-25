package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.StatusVisitaDTO;
import com.jacto.agendamento.entity.StatusVisita;
import com.jacto.agendamento.service.StatusVisitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/status-visita")
public class StatusVisitaController {

    @Autowired
    private StatusVisitaService service;

    private StatusVisitaDTO convertToDto(StatusVisita entity) {
        return new StatusVisitaDTO(entity.getCodigo(), entity.getDescricao());
    }

    private StatusVisita convertToEntity(StatusVisitaDTO dto) {
        StatusVisita entity = new StatusVisita();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

    @GetMapping
    public List<StatusVisitaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<StatusVisitaDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<StatusVisita> opt = service.buscarPorCodigo(codigo);
        return opt.map(s -> ResponseEntity.ok(convertToDto(s)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StatusVisitaDTO> salvar(@Valid @RequestBody StatusVisitaDTO dto) {
        StatusVisita entity = convertToEntity(dto);
        StatusVisita salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<StatusVisitaDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody StatusVisitaDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(s -> {
                s.setDescricao(dto.getDescricao());
                StatusVisita atualizado = service.salvar(s);
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
}