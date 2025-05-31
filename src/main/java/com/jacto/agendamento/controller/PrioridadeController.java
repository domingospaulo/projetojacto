package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.PrioridadeDTO;
import com.jacto.agendamento.entity.Prioridade;
import com.jacto.agendamento.service.PrioridadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/prioridades")
public class PrioridadeController {

    @Autowired
    private PrioridadeService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<PrioridadeDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PrioridadeDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Prioridade> opt = service.buscarPorCodigo(codigo);
        return opt.map(p -> ResponseEntity.ok(convertToDto(p)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PrioridadeDTO> salvar(@Valid @RequestBody PrioridadeDTO dto) {
        Prioridade entity = convertToEntity(dto);
        Prioridade salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PrioridadeDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody PrioridadeDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(p -> {
                p.setDescricao(dto.getDescricao());
                Prioridade atualizado = service.salvar(p);
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
 
    private PrioridadeDTO convertToDto(Prioridade entity) {
        return new PrioridadeDTO(entity.getCodigo(), entity.getDescricao());
    }

    private Prioridade convertToEntity(PrioridadeDTO dto) {
        Prioridade entity = new Prioridade();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }
   
}