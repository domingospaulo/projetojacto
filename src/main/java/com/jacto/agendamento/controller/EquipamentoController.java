package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.EquipamentoDTO;
import com.jacto.agendamento.controller.requests.EquipamentoRequest;
import com.jacto.agendamento.entity.Equipamento;
import com.jacto.agendamento.service.EquipamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<EquipamentoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EquipamentoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Equipamento> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EquipamentoDTO> salvar(@Valid @RequestBody EquipamentoRequest request) {
        Equipamento entity = convertToEntity(request);
        Equipamento salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EquipamentoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody EquipamentoRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(request.getDescricao());
                Equipamento atualizado = service.salvar(c);
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

    // Converte entidade para DTO
    private EquipamentoDTO convertToDto(Equipamento entity) {
        return new EquipamentoDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converte DTO para entidade
    private Equipamento convertToEntity(EquipamentoRequest request) {
        Equipamento entity = new Equipamento();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }

}