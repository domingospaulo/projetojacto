package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.AvaliacaoAtendimentoDTO;
import com.jacto.agendamento.controller.dto.FazendaDTO;
import com.jacto.agendamento.controller.dto.FuncionarioDTO;
import com.jacto.agendamento.controller.dto.OcorrenciaDTO;
import com.jacto.agendamento.controller.dto.PrioridadeDTO;
import com.jacto.agendamento.controller.dto.StatusVisitaDTO;
import com.jacto.agendamento.controller.dto.TipoServicoDTO;
import com.jacto.agendamento.controller.dto.VisitaTecnicaDTO;
import com.jacto.agendamento.controller.requests.AvaliacaoAtendimentoRequest;
import com.jacto.agendamento.entity.AvaliacaoAtendimento;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.AvaliacaoAtendimentoService;
import com.jacto.agendamento.service.VisitaTecnicaService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<AvaliacaoAtendimentoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<AvaliacaoAtendimentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<AvaliacaoAtendimento> opt = service.buscarPorId(id);
        return opt.map(e -> ResponseEntity.ok(convertToDto(e))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('100') or hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<AvaliacaoAtendimentoDTO> salvar(@Valid @RequestBody AvaliacaoAtendimentoRequest request) {
        AvaliacaoAtendimento entity = convertToEntity(request);
        AvaliacaoAtendimento salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
        }

    @GetMapping("/cliente/{matricula}")
    @PreAuthorize("hasAuthority('100') or hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<List<AvaliacaoAtendimento>> buscarPorMatriculaCliente(@PathVariable Long matricula) {
        List<AvaliacaoAtendimento> avaliacoes = service.buscarPorMatriculaCliente(matricula);
        return ResponseEntity.ok(avaliacoes);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<AvaliacaoAtendimentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AvaliacaoAtendimentoRequest request) {
        return service.buscarPorId(id)
            .map(existing -> {
                AvaliacaoAtendimento entity = convertToEntity(request);
                entity.setId(existing.getId());
                AvaliacaoAtendimento salvo = service.salvar(entity);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
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

    // Método de conversão entidade --> DTO
   private AvaliacaoAtendimentoDTO convertToDto(AvaliacaoAtendimento entity) {
        AvaliacaoAtendimentoDTO dto = new AvaliacaoAtendimentoDTO();
        dto.setId(entity.getId());
        dto.setAvaliacao(entity.getAvaliacao());
        dto.setObservacao(entity.getObservacao());
        dto.setDataHoraOperacao(entity.getDataHoraOperacao());

        if (entity.getVisita() != null) {
            VisitaTecnicaDTO visitaTecnicaDTO = new VisitaTecnicaDTO();
            visitaTecnicaDTO.setId(entity.getVisita().getId());
            if (entity.getVisita().getFuncionario() != null) {
               FuncionarioDTO dto1 = new FuncionarioDTO();
               dto1.setMatricula(entity.getVisita().getFuncionario().getMatricula());
                visitaTecnicaDTO.setFuncionarioDTO(dto1);
            }

            FazendaDTO fazendaDto = new FazendaDTO();
            fazendaDto.setId(entity.getVisita().getFazenda().getId());
            visitaTecnicaDTO.setFazendaDTO(fazendaDto);

            TipoServicoDTO tipoServicoDto = new TipoServicoDTO();
            tipoServicoDto.setCodigo(entity.getVisita().getTipoServico().getCodigo());
            visitaTecnicaDTO.setTipoServicoDTO(tipoServicoDto);
    
            PrioridadeDTO prioridadeDto = new PrioridadeDTO();
            prioridadeDto.setCodigo(entity.getVisita().getPrioridade().getCodigo());
            visitaTecnicaDTO.setPrioridadeDTO(prioridadeDto);

            StatusVisitaDTO statusVisitaDto = new StatusVisitaDTO();
            statusVisitaDto.setCodigo(entity.getVisita().getStatusVisita().getCodigo());
            visitaTecnicaDTO.setStatusVisitaDTO(statusVisitaDto);

            OcorrenciaDTO ocorrenciaDto = new OcorrenciaDTO();
            ocorrenciaDto.setCodigo(entity.getVisita().getOcorrencia().getCodigo());
            visitaTecnicaDTO.setOcorrenciaDTO(ocorrenciaDto);

            visitaTecnicaDTO.setDataHoraAgendamento(entity.getVisita().getDataHoraAgendamento());
            visitaTecnicaDTO.setDataHoraVisitaInicioAgendado(entity.getVisita().getDataHoraVisitaInicioAgendado());
            visitaTecnicaDTO.setDataHoraVisitaFimAgendado(entity.getVisita().getDataHoraVisitaFimAgendado());
            visitaTecnicaDTO.setDataHoraVisitaInicio(entity.getVisita().getDataHoraVisitaInicio());
            visitaTecnicaDTO.setDataHoraVisitaFim(entity.getVisita().getDataHoraVisitaFim());
            visitaTecnicaDTO.setObservacao(entity.getVisita().getObservacao());
            visitaTecnicaDTO.setFlagReagendamento(entity.getVisita().getFlagReagendamento());
            dto.setVisitaTecnicaDTO(visitaTecnicaDTO);
        }

        return dto;
    }

    // Método de conversão DTO --> entidade
    private AvaliacaoAtendimento convertToEntity(AvaliacaoAtendimentoRequest request) {
        AvaliacaoAtendimento entity = new AvaliacaoAtendimento();
 
        // Buscar a visita técnica pelo ID fornecido
        Optional<VisitaTecnica> visitaOpt = visitaService.buscarPorId(request.getIdVisitaTecnica());
        if (!visitaOpt.isPresent()) {
            throw new IllegalArgumentException("Visita técnica não encontrada para o ID: " + request.getIdVisitaTecnica());
        }
        entity.setVisita(visitaOpt.get());

        entity.setAvaliacao(request.getAvaliacao());
        entity.setObservacao(request.getObservacao());
        entity.setDataHoraOperacao(request.getDataHoraOperacao() != null ? request.getDataHoraOperacao() : new java.util.Date());

        return entity;
    }

}