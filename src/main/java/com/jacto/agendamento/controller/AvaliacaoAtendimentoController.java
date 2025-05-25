package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.AvaliacaoAtendimentoDTO;
import com.jacto.agendamento.entity.AvaliacaoAtendimento;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.AvaliacaoAtendimentoService;
import com.jacto.agendamento.service.VisitaTecnicaService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoAtendimentoController {

    @Autowired
    private AvaliacaoAtendimentoService service;

    @Autowired
    private VisitaTecnicaService visitaService;

    @GetMapping
    public List<AvaliacaoAtendimentoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoAtendimentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<AvaliacaoAtendimento> opt = service.buscarPorId(id);
        return opt.map(e -> ResponseEntity.ok(convertToDto(e))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AvaliacaoAtendimentoDTO> salvar(@Valid @RequestBody AvaliacaoAtendimentoDTO dto) {
        AvaliacaoAtendimento entity = convertToEntity(dto);
        AvaliacaoAtendimento salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
        }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoAtendimentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AvaliacaoAtendimentoDTO dto) {
        return service.buscarPorId(id)
            .map(existing -> {
                AvaliacaoAtendimento entity = convertToEntity(dto);
                entity.setId(existing.getId());
                AvaliacaoAtendimento salvo = service.salvar(entity);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método de conversão entidade --> DTO
    private AvaliacaoAtendimentoDTO convertToDto(AvaliacaoAtendimento entity) {
        AvaliacaoAtendimentoDTO dto = new AvaliacaoAtendimentoDTO();
        dto.setId(entity.getId());
        dto.setAvaliacao(entity.getAvaliacao());
        dto.setObservacao(entity.getObservacao());
        dto.setDataHoraOperacao(entity.getDataHoraOperacao());
        if (entity.getVisita() != null) {
            dto.setIdVisitaTecnica(entity.getVisita().getId());
        }

        return dto;
    }

    // Método de conversão DTO --> entidade
    private AvaliacaoAtendimento convertToEntity(AvaliacaoAtendimentoDTO dto) {
        AvaliacaoAtendimento entity = new AvaliacaoAtendimento();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        // Buscar a visita técnica pelo ID fornecido
        Optional<VisitaTecnica> visitaOpt = visitaService.buscarPorId(dto.getIdVisitaTecnica());
        if (!visitaOpt.isPresent()) {
            throw new IllegalArgumentException("Visita técnica não encontrada para o ID: " + dto.getIdVisitaTecnica());
        }
        entity.setVisita(visitaOpt.get());

        entity.setAvaliacao(dto.getAvaliacao());
        entity.setObservacao(dto.getObservacao());
        entity.setDataHoraOperacao(dto.getDataHoraOperacao() != null ? dto.getDataHoraOperacao() : new java.util.Date());

        return entity;
    }

}