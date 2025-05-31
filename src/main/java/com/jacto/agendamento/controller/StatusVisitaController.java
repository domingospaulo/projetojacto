package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.StatusVisitaDTO;
import com.jacto.agendamento.controller.requests.StatusVisitaRequest;
import com.jacto.agendamento.entity.StatusVisita;
import com.jacto.agendamento.service.StatusVisitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/status-visita")
public class StatusVisitaController {

    @Autowired
    private StatusVisitaService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<StatusVisitaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<StatusVisitaDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<StatusVisita> opt = service.buscarPorCodigo(codigo);
        return opt.map(s -> ResponseEntity.ok(convertToDto(s)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<StatusVisitaDTO> salvar(@Valid @RequestBody StatusVisitaRequest request) {
        StatusVisita entity = convertToEntity(request);
        StatusVisita salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<StatusVisitaDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody StatusVisitaRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(s -> {
                s.setDescricao(request.getDescricao());
                StatusVisita atualizado = service.salvar(s);
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

    private StatusVisitaDTO convertToDto(StatusVisita entity) {
        return new StatusVisitaDTO(entity.getCodigo(), entity.getDescricao());
    }

    private StatusVisita convertToEntity(StatusVisitaRequest request) {
        StatusVisita entity = new StatusVisita();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }
}