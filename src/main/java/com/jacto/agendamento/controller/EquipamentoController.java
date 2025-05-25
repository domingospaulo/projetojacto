package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.EquipamentoDTO;
import com.jacto.agendamento.entity.Equipamento;
import com.jacto.agendamento.service.EquipamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    // Converte entidade para DTO
    private EquipamentoDTO convertToDto(Equipamento entity) {
        return new EquipamentoDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converte DTO para entidade
    private Equipamento convertToEntity(EquipamentoDTO dto) {
        Equipamento entity = new Equipamento();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

    @GetMapping
    public List<EquipamentoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<EquipamentoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Equipamento> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EquipamentoDTO> salvar(@Valid @RequestBody EquipamentoDTO dto) {
        Equipamento entity = convertToEntity(dto);
        Equipamento salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<EquipamentoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody EquipamentoDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(dto.getDescricao());
                Equipamento atualizado = service.salvar(c);
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