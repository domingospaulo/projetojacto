package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.EstoqueEquipamentoDTO;
import com.jacto.agendamento.entity.EstoqueEquipamento;
import com.jacto.agendamento.service.EstoqueEquipamentoService;
import com.jacto.agendamento.service.EquipamentoService; // Para buscar equipamento

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/estoque-equipamentos")
public class EstoqueEquipamentoController {

    @Autowired
    private EstoqueEquipamentoService service;

    @Autowired
    private EquipamentoService equipamentoService; 

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<EstoqueEquipamentoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoqueEquipamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EstoqueEquipamento> opt = service.buscarPorId(id);
        return opt.map(e -> ResponseEntity.ok(convertToDto(e))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo-equipment/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<EstoqueEquipamentoDTO> buscarPorCodigoEquipamento(@PathVariable Integer codigo) {
        return service.buscarPorCodigoEquipamento(codigo)
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoqueEquipamentoDTO> salvar(@Valid @RequestBody EstoqueEquipamentoDTO dto) {
        EstoqueEquipamento entity = convertToEntity(dto);
        EstoqueEquipamento salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<EstoqueEquipamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueEquipamentoDTO dto) {
        return service.buscarPorId(id)
            .map(e -> {
                EstoqueEquipamento updated = convertToEntity(dto);
                updated.setId(e.getId()); // garante que a id permanece
                EstoqueEquipamento salvo = service.salvar(updated);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private EstoqueEquipamentoDTO convertToDto(EstoqueEquipamento entity) {
        return new EstoqueEquipamentoDTO(
            entity.getEquipamento().getCodigo(),
            entity.getQuantidade()
        );
    }

    private EstoqueEquipamento convertToEntity(EstoqueEquipamentoDTO dto) {
        EstoqueEquipamento entity = new EstoqueEquipamento();

        // Buscar aEquipment pelo código
        equipamentoService.buscarPorCodigo(dto.getCodigoEquipamento())
            .ifPresentOrElse(
                eq -> entity.setEquipamento(eq),
                () -> { throw new IllegalArgumentException("Equipamento não encontrado com código: " + dto.getCodigoEquipamento()); }
            );

        entity.setQuantidade(dto.getQuantidade());

        return entity;
    }

}