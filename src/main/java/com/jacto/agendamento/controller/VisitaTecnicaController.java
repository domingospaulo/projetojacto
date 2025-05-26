package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.VisitaTecnicaDTO;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.VisitaTecnicaService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.FazendaService;
import com.jacto.agendamento.service.TipoServicoService;
import com.jacto.agendamento.service.PrioridadeService;
import com.jacto.agendamento.service.StatusVisitaService;
import com.jacto.agendamento.service.OcorrenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visitas")
public class VisitaTecnicaController {

    @Autowired private VisitaTecnicaService service;
    @Autowired private FazendaService fazendaService;
    @Autowired private FuncionarioService funcionarioService;
    @Autowired private TipoServicoService tipoServicoService;
    @Autowired private PrioridadeService prioridadeService;
    @Autowired private StatusVisitaService statusVisitaService;
    @Autowired private OcorrenciaService ocorrenciaService;

    // Listar todos
    @GetMapping
    public List<VisitaTecnicaDTO> listarTodos() {
        return service.listarTodos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<VisitaTecnicaDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VisitaTecnicaDTO> criar(@Valid @RequestBody VisitaTecnicaDTO dto) {
         // Verifica se pelo menos um relacionamentos foi informado
        if (dto.getMatriculaFuncionario() == null ) {
            throw new IllegalArgumentException("É obrigatório informar matricula do funcionário");
        }
       
        VisitaTecnica entity = convertToEntity(dto);
        VisitaTecnica salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    // Atualizar uma visita existente
    @PutMapping("/{id}")
    public ResponseEntity<VisitaTecnicaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VisitaTecnicaDTO dto) {
        return service.buscarPorId(id)
                .map(existingVisita -> {
                    VisitaTecnica updatedVisita = convertToEntity(dto);
                    updatedVisita.setId(id); // Garante que o ID seja o mesmo
                    VisitaTecnica savedVisita = service.salvar(updatedVisita);
                    return ResponseEntity.ok(convertToDto(savedVisita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar uma visita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // Métodos de busca por outros parâmetros (opcional)
    @GetMapping("/funcionario/{matriculaFuncionario}")
    public List<VisitaTecnicaDTO> buscarPorMatriculaFuncionario(@PathVariable Long matriculaFuncionario) {
        return service.buscarPorMatriculaFuncionario(matriculaFuncionario)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/fazenda/{idFazenda}")
    public List<VisitaTecnicaDTO> buscarPorIdFazenda(@PathVariable Long idFazenda) {
        return service.buscarPorIdFazenda(idFazenda)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

   @GetMapping("/tipo-servico/{codigoTipoServico}")
    public List<VisitaTecnicaDTO> buscarPorCodigoTipoServico(@PathVariable Integer codigoTipoServico) {
        return service.buscarPorCodigoTipoServico(codigoTipoServico)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/prioridade/{codigoPrioridade}")
    public List<VisitaTecnicaDTO> buscarPorCodigoPrioridade(@PathVariable Integer codigoPrioridade) {
        return service.buscarPorCodigoPrioridade(codigoPrioridade)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/status-visita/{codigoStatusVisita}")
    public List<VisitaTecnicaDTO> buscarPorCodigoStatusVisita(@PathVariable Integer codigoStatusVisita) {
        return service.buscarPorCodigoStatusVisita(codigoStatusVisita)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ocorrencia/{codigoOcorrencia}")
    public List<VisitaTecnicaDTO> buscarPorCodigoOcorrencia(@PathVariable Integer codigoOcorrencia) {
        return service.buscarPorCodigoOcorrencia(codigoOcorrencia)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
     // Converte entidade para DTO
    private VisitaTecnicaDTO convertToDto(VisitaTecnica entity) {
        VisitaTecnicaDTO dto = new VisitaTecnicaDTO();
        dto.setId(entity.getId());
        if (entity.getFuncionario() != null)
            dto.setMatriculaFuncionario(entity.getFuncionario().getMatricula());
        if (entity.getFazenda() != null)
            dto.setIdFazenda(entity.getFazenda().getId());
        if (entity.getTipoServico() != null)
            dto.setIdTipoServico(entity.getTipoServico().getCodigo());
        if (entity.getPrioridade() != null)
            dto.setIdPrioridade(entity.getPrioridade().getCodigo());
        if (entity.getStatusVisita() != null)
            dto.setIdStatusVisita(entity.getStatusVisita().getCodigo());
        if (entity.getOcorrencia() != null)
            dto.setCodigoOcorrencia(entity.getOcorrencia().getCodigo());
        if (entity.getVisitaRef() != null)
            dto.setIdVisitaTecnica(entity.getVisitaRef().getId());
        dto.setDataHoraAgendamento(entity.getDataHoraAgendamento());
        dto.setDataHoraVisitaInicioAgendado(entity.getDataHoraVisitaInicioAgendado());
        dto.setDataHoraVisitaFimAgendado(entity.getDataHoraVisitaFimAgendado());
        dto.setDataHoraVisitaInicio(entity.getDataHoraVisitaInicio());
        dto.setDataHoraVisitaFim(entity.getDataHoraVisitaFim());
        dto.setObservacao(entity.getObservacao());
        dto.setFlagReagendamento(entity.getFlagReagendamento());
        return dto;
    }

    // Converte DTO para entidade
    private VisitaTecnica convertToEntity(VisitaTecnicaDTO dto) {
        VisitaTecnica entity = new VisitaTecnica();
        entity.setId(dto.getId());

        // Busca as entidades relacionadas pelos IDs
        entity.setFuncionario(funcionarioService.buscarPorMatricula(dto.getMatriculaFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionario não encontrado com a matricula: " + dto.getMatriculaFuncionario())));

        entity.setFazenda(fazendaService.buscarPorId(dto.getIdFazenda())
                .orElseThrow(() -> new IllegalArgumentException("Fazenda não encontrada com o ID: " + dto.getIdFazenda())));

        entity.setTipoServico(tipoServicoService.buscarPorCodigo(dto.getIdTipoServico())
                .orElseThrow(() -> new IllegalArgumentException("TipoServico não encontrado com o código: " + dto.getIdTipoServico())));

        entity.setPrioridade(prioridadeService.buscarPorCodigo(dto.getIdPrioridade())
                .orElseThrow(() -> new IllegalArgumentException("Prioridade não encontrada com o código: " + dto.getIdPrioridade())));

        entity.setStatusVisita(statusVisitaService.buscarPorCodigo(dto.getIdStatusVisita())
                .orElseThrow(() -> new IllegalArgumentException("StatusVisita não encontrado com o código: " + dto.getIdStatusVisita())));

        entity.setOcorrencia(ocorrenciaService.buscarPorCodigo(dto.getCodigoOcorrencia())
                .orElseThrow(() -> new IllegalArgumentException("Ocorrencia não encontrada com o código: " + dto.getCodigoOcorrencia())));

        //Auto-relacionamento (Visita Técnica)
        if(dto.getIdVisitaTecnica() != null) {
             entity.setVisitaRef(service.buscarPorId(dto.getIdVisitaTecnica()).orElse(null));
        }

        entity.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        entity.setDataHoraVisitaInicioAgendado(dto.getDataHoraVisitaInicioAgendado());
        entity.setDataHoraVisitaFimAgendado(dto.getDataHoraVisitaFimAgendado());
        entity.setDataHoraVisitaInicio(dto.getDataHoraVisitaInicio());
        entity.setDataHoraVisitaFim(dto.getDataHoraVisitaFim());
        entity.setObservacao(dto.getObservacao());
        entity.setFlagReagendamento(dto.getFlagReagendamento());

        return entity;
    }
}