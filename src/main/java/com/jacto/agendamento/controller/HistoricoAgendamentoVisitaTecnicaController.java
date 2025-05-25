package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.HistoricoAgendamentoVisitaTecnicaDTO;
import com.jacto.agendamento.entity.HistoricoAgendamentoVisitaTecnica;
import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.entity.Usuario;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.HistoricoAgendamentoVisitaTecnicaService;
import com.jacto.agendamento.service.VisitaTecnicaService;
import com.jacto.agendamento.service.OperacaoService;
import com.jacto.agendamento.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/historico-visita")
public class HistoricoAgendamentoVisitaTecnicaController {

    @Autowired
    private HistoricoAgendamentoVisitaTecnicaService service;

    @Autowired
    private VisitaTecnicaService visitaService;

    @Autowired
    private OperacaoService operacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<HistoricoAgendamentoVisitaTecnicaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoAgendamentoVisitaTecnicaDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/visita/{idVisita}")
    public ResponseEntity<List<HistoricoAgendamentoVisitaTecnicaDTO>> buscarPorIdVisita(@PathVariable Long idVisita) {
        List<HistoricoAgendamentoVisitaTecnica> lista = service.buscarPorIdVisita(idVisita);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<HistoricoAgendamentoVisitaTecnicaDTO> dtos = lista.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<HistoricoAgendamentoVisitaTecnicaDTO> salvar(@Valid @RequestBody HistoricoAgendamentoVisitaTecnicaDTO dto) {
        HistoricoAgendamentoVisitaTecnica entity = convertToEntity(dto);
        HistoricoAgendamentoVisitaTecnica salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }
        
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoAgendamentoVisitaTecnicaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody HistoricoAgendamentoVisitaTecnicaDTO dto) {
        return service.buscarPorId(id)
            .map(existing -> {
                HistoricoAgendamentoVisitaTecnica updated = convertToEntity(dto);
                updated.setId(existing.getId()); // mantém o ID original
                HistoricoAgendamentoVisitaTecnica salvo = service.salvar(updated);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Converter entidade para DTO
    private HistoricoAgendamentoVisitaTecnicaDTO convertToDto(HistoricoAgendamentoVisitaTecnica entity) {
        HistoricoAgendamentoVisitaTecnicaDTO dto = new HistoricoAgendamentoVisitaTecnicaDTO();
        dto.setId(entity.getId());

        if (entity.getVisitaTecnica() != null)
            dto.setIdVisitaTecnica(entity.getVisitaTecnica().getId());

        if (entity.getOperacao() != null)
            dto.setCodigoOperacao(entity.getOperacao().getCodigo());

        if (entity.getUsuario() != null)
            dto.setLoginUsuario(entity.getUsuario().getLogin());

        dto.setDataHoraOperacao(entity.getDataHoraOperacao());
        dto.setObservacao(entity.getObservacao());
        return dto;
    }

    // Converter DTO para entidade
    private HistoricoAgendamentoVisitaTecnica convertToEntity(HistoricoAgendamentoVisitaTecnicaDTO dto) {
        HistoricoAgendamentoVisitaTecnica entity = new HistoricoAgendamentoVisitaTecnica();

        if (dto.getId() != null)
            entity.setId(dto.getId());

        // Buscar visita técnica
        Optional<VisitaTecnica> visitaOpt = visitaService.buscarPorId(dto.getIdVisitaTecnica());
        if (!visitaOpt.isPresent())
            throw new IllegalArgumentException("Visita técnica não encontrada para ID: " + dto.getIdVisitaTecnica());
        entity.setVisitaTecnica(visitaOpt.get());

        // Buscar operação
        Optional<Operacao> operacaoOpt = operacaoService.buscarPorCodigo(dto.getCodigoOperacao());
        if (!operacaoOpt.isPresent())
            throw new IllegalArgumentException("Operação não encontrada para código: " + dto.getCodigoOperacao());
        entity.setOperacao(operacaoOpt.get());

        // Buscar usuário
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorLogin(dto.getLoginUsuario());
        if (!usuarioOpt.isPresent())
            throw new IllegalArgumentException("Usuário não encontrado para login: " + dto.getLoginUsuario());
        entity.setUsuario(usuarioOpt.get());

        entity.setDataHoraOperacao(dto.getDataHoraOperacao() != null ? dto.getDataHoraOperacao() : new Date());
        entity.setObservacao(dto.getObservacao());

        return entity;
    }
}