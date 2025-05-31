package com.jacto.agendamento.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacto.agendamento.controller.dto.AvaliacaoAtendimentoDTO;
import com.jacto.agendamento.controller.dto.EquipamentoDTO;
import com.jacto.agendamento.controller.dto.EquipamentoVisitaTecnicaDTO;
import com.jacto.agendamento.controller.dto.FazendaDTO;
import com.jacto.agendamento.controller.dto.FuncionarioDTO;
import com.jacto.agendamento.controller.dto.OcorrenciaDTO;
import com.jacto.agendamento.controller.dto.PecaReposicaoDTO;
import com.jacto.agendamento.controller.dto.PecaReposicaoVisitaTecnicaDTO;
import com.jacto.agendamento.controller.dto.PrioridadeDTO;
import com.jacto.agendamento.controller.dto.StatusVisitaDTO;
import com.jacto.agendamento.controller.dto.TipoServicoDTO;
import com.jacto.agendamento.controller.dto.VisitaTecnicaDTO;
import com.jacto.agendamento.controller.requests.VisitaTecnicaRequest;
import com.jacto.agendamento.entity.EquipamentosVisitaTecnica;
import com.jacto.agendamento.entity.PecasReposicaoVisitaTecnica;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.EquipamentoService;
import com.jacto.agendamento.service.FazendaService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.OcorrenciaService;
import com.jacto.agendamento.service.PecaReposicaoService;
import com.jacto.agendamento.service.PrioridadeService;
import com.jacto.agendamento.service.StatusVisitaService;
import com.jacto.agendamento.service.TipoServicoService;
import com.jacto.agendamento.service.VisitaTecnicaService;

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
    @Autowired private EquipamentoService equipamentoService;
    @Autowired private PecaReposicaoService pecaReposicaoService;

    // Listar todos
    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> listarTodos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        return service.listarTodos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<VisitaTecnicaDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<VisitaTecnicaDTO> criar(@Valid @RequestBody VisitaTecnicaRequest request) {
         // Verifica se pelo menos um relacionamentos foi informado
        if (request.getMatriculaFuncionario() == null ) {
            throw new IllegalArgumentException("É obrigatório informar matricula do funcionário");
        }
        
         // Valida a data e hora do agendamento
        if (request.getDataHoraVisitaInicioAgendado() != null && request.getDataHoraVisitaInicioAgendado().before(new Date())) {
            throw new IllegalArgumentException("Não é possível agendar a visita para uma data e hora anterior à data e hora atual.");
        }
      
        VisitaTecnica entity = convertToEntity(request);
        VisitaTecnica salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    // Atualizar uma visita existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<VisitaTecnicaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VisitaTecnicaRequest request) {
        return service.buscarPorId(id)
                .map(existingVisita -> {
                    // Valida a data e hora do agendamento
                    if (request.getDataHoraVisitaInicioAgendado() != null && request.getDataHoraVisitaInicioAgendado().before(new Date())) {
                        throw new IllegalArgumentException("Não é possível agendar a visita para uma data e hora anterior à data e hora atual.");
                    }

                    VisitaTecnica updatedVisita = convertToEntity(request);
                    updatedVisita.setId(id); // Garante que o ID seja o mesmo
                    VisitaTecnica savedVisita = service.salvar(updatedVisita);
                    return ResponseEntity.ok(convertToDto(savedVisita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar uma visita
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // Métodos de busca por outros parâmetros (opcional)
    @GetMapping("/funcionario/{matriculaFuncionario}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> buscarPorMatriculaFuncionario(@PathVariable Long matriculaFuncionario) {
        return service.buscarPorMatriculaFuncionario(matriculaFuncionario)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/fazenda/{idFazenda}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> buscarPorIdFazenda(@PathVariable Long idFazenda) {
        return service.buscarPorIdFazenda(idFazenda)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/tipo-servico/{codigoTipoServico}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")   
    public List<VisitaTecnicaDTO> buscarPorCodigoTipoServico(@PathVariable Integer codigoTipoServico) {
        return service.buscarPorCodigoTipoServico(codigoTipoServico)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/prioridade/{codigoPrioridade}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> buscarPorCodigoPrioridade(@PathVariable Integer codigoPrioridade) {
        return service.buscarPorCodigoPrioridade(codigoPrioridade)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/status-visita/{codigoStatusVisita}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> buscarPorCodigoStatusVisita(@PathVariable Integer codigoStatusVisita) {
        return service.buscarPorCodigoStatusVisita(codigoStatusVisita)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ocorrencia/{codigoOcorrencia}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<VisitaTecnicaDTO> buscarPorCodigoOcorrencia(@PathVariable Integer codigoOcorrencia) {
        return service.buscarPorCodigoOcorrencia(codigoOcorrencia)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VisitaTecnicaDTO convertToDto(VisitaTecnica entity) {
        VisitaTecnicaDTO dto = new VisitaTecnicaDTO();
        dto.setId(entity.getId());

        // Converte Funcionario para FuncionarioDTO
        if (entity.getFuncionario() != null) {
            FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
            funcionarioDTO.setMatricula(entity.getFuncionario().getMatricula());
            dto.setFuncionarioDTO(funcionarioDTO);
        }

        // Converte Fazenda para FazendaDTO
        if (entity.getFazenda() != null) {
            FazendaDTO fazendaDTO = new FazendaDTO();
            fazendaDTO.setId(entity.getFazenda().getId());
            dto.setFazendaDTO(fazendaDTO);
        }

        // Converte TipoServico para TipoServicoDTO
        if (entity.getTipoServico() != null) {
            TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
            tipoServicoDTO.setCodigo(entity.getTipoServico().getCodigo());
            dto.setTipoServicoDTO(tipoServicoDTO);
        }

        // Converte Prioridade para PrioridadeDTO
        if (entity.getPrioridade() != null) {
            PrioridadeDTO prioridadeDTO = new PrioridadeDTO();
            prioridadeDTO.setCodigo(entity.getPrioridade().getCodigo());
            dto.setPrioridadeDTO(prioridadeDTO);
        }

        // Converte StatusVisita para StatusVisitaDTO
        if (entity.getStatusVisita() != null) {
            StatusVisitaDTO statusVisitaDTO = new StatusVisitaDTO();
            statusVisitaDTO.setCodigo(entity.getStatusVisita().getCodigo());
            dto.setStatusVisitaDTO(statusVisitaDTO);
        }

        // Converte Ocorrencia para OcorrenciaDTO
        if (entity.getOcorrencia() != null) {
            OcorrenciaDTO ocorrenciaDTO = new OcorrenciaDTO();
            ocorrenciaDTO.setCodigo(entity.getOcorrencia().getCodigo());
            dto.setOcorrenciaDTO(ocorrenciaDTO);
        }

        // Converte VisitaTecnica (auto-relacionamento) para VisitaTecnicaDTO
        if (entity.getVisitaRef() != null) {
            VisitaTecnicaDTO visitaTecnicaReferenciaDTO = new VisitaTecnicaDTO();
            visitaTecnicaReferenciaDTO.setId(entity.getVisitaRef().getId());
            dto.setVisitaTecnicaReferenciaDTO(visitaTecnicaReferenciaDTO);
        }

        dto.setDataHoraAgendamento(entity.getDataHoraAgendamento());
        dto.setDataHoraVisitaInicioAgendado(entity.getDataHoraVisitaInicioAgendado());
        dto.setDataHoraVisitaFimAgendado(entity.getDataHoraVisitaFimAgendado());
        dto.setDataHoraVisitaInicio(entity.getDataHoraVisitaInicio());
        dto.setDataHoraVisitaFim(entity.getDataHoraVisitaFim());
        dto.setObservacao(entity.getObservacao());
        dto.setFlagReagendamento(entity.getFlagReagendamento());

         // Converte EquipamentosVisitaTecnica para EquipamentoVisitaTecnicaDTO
        if (entity.getEquipamentosVisitaTecnica() != null) {
            dto.setEquipamentosVisitaTecnica(entity.getEquipamentosVisitaTecnica().stream()
                .map(eq -> {
                   EquipamentoVisitaTecnicaDTO eqDto = new EquipamentoVisitaTecnicaDTO();
                  EquipamentoDTO equipamentoDTO = new EquipamentoDTO();
                   equipamentoDTO.setCodigo(eq.getEquipamento().getCodigo());
                    eqDto.setEquipamentoDTO(equipamentoDTO);
                    eqDto.setQtde(eq.getQtde());
                    return eqDto;
                })
                .collect(Collectors.toList()));
        }

        // Converte PecasReposicaoVisitaTecnica para PecaReposicaoVisitaTecnicaDTO
        if (entity.getPecasReposicaoVisitaTecnica() != null) {
            dto.setPecasReposicaoVisitaTecnica(entity.getPecasReposicaoVisitaTecnica().stream()
                .map(peca -> {
                    PecaReposicaoVisitaTecnicaDTO pecaDto = new PecaReposicaoVisitaTecnicaDTO();
                     PecaReposicaoDTO pecaReposicaoDTO = new PecaReposicaoDTO();
                     pecaReposicaoDTO.setCodigo(peca.getPecaReposicao().getCodigo());
                    pecaDto.setPecaReposicaoDTO(pecaReposicaoDTO);
                    pecaDto.setQtde(peca.getQtde());
                    return pecaDto;
                })
                .collect(Collectors.toList()));
        }

        // Converte AvaliacaoAtendimento para AvaliacaoAtendimentoDTO
        if (entity.getAvaliacaoAtendimento() != null) {
            AvaliacaoAtendimentoDTO avaliacaoAtendimentoDTO = new AvaliacaoAtendimentoDTO();
            avaliacaoAtendimentoDTO.setId(entity.getAvaliacaoAtendimento().getId());
            avaliacaoAtendimentoDTO.setAvaliacao(entity.getAvaliacaoAtendimento().getAvaliacao());
            avaliacaoAtendimentoDTO.setObservacao(entity.getAvaliacaoAtendimento().getObservacao());
            avaliacaoAtendimentoDTO.setDataHoraOperacao(entity.getAvaliacaoAtendimento().getDataHoraOperacao());
            dto.setAvaliacaoAtendimentoDTO(avaliacaoAtendimentoDTO);
        }

        return dto;
    }

    // Converte DTO para entidade
    private VisitaTecnica convertToEntity(VisitaTecnicaRequest request) {
        VisitaTecnica entity = new VisitaTecnica();

        // Busca as entidades relacionadas pelos IDs
        entity.setFuncionario(funcionarioService.buscarPorMatricula(request.getMatriculaFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionario não encontrado com a matricula: " + request.getMatriculaFuncionario())));

        entity.setFazenda(fazendaService.buscarPorId(request.getIdFazenda())
                .orElseThrow(() -> new IllegalArgumentException("Fazenda não encontrada com o ID: " + request.getIdFazenda())));

        entity.setTipoServico(tipoServicoService.buscarPorCodigo(request.getIdTipoServico())
                .orElseThrow(() -> new IllegalArgumentException("TipoServico não encontrado com o código: " + request.getIdTipoServico())));

        entity.setPrioridade(prioridadeService.buscarPorCodigo(request.getIdPrioridade())
                .orElseThrow(() -> new IllegalArgumentException("Prioridade não encontrada com o código: " + request.getIdPrioridade())));

        entity.setStatusVisita(statusVisitaService.buscarPorCodigo(request.getIdStatusVisita())
                .orElseThrow(() -> new IllegalArgumentException("StatusVisita não encontrado com o código: " + request.getIdStatusVisita())));

        entity.setOcorrencia(ocorrenciaService.buscarPorCodigo(request.getCodigoOcorrencia())
                .orElseThrow(() -> new IllegalArgumentException("Ocorrencia não encontrada com o código: " + request.getCodigoOcorrencia())));

        //Auto-relacionamento (Visita Técnica)
        if(request.getIdVisitaTecnica() != null) {
             entity.setVisitaRef(service.buscarPorId(request.getIdVisitaTecnica()).orElse(null));
        }

        entity.setDataHoraAgendamento(request.getDataHoraAgendamento());
        entity.setDataHoraVisitaInicioAgendado(request.getDataHoraVisitaInicioAgendado());
        entity.setDataHoraVisitaFimAgendado(request.getDataHoraVisitaFimAgendado());
        entity.setDataHoraVisitaInicio(request.getDataHoraVisitaInicio());
        entity.setDataHoraVisitaFim(request.getDataHoraVisitaFim());
        entity.setObservacao(request.getObservacao());
        entity.setFlagReagendamento(request.getFlagReagendamento());

        // Converte EquipamentoVisitaTecnicarequest para EquipamentoVisitaTecnica
        if (request.getEquipamentosVisitaTecnica() != null) {
            entity.setEquipamentosVisitaTecnica(request.getEquipamentosVisitaTecnica().stream()
                .map(eqDto -> {
                    EquipamentosVisitaTecnica eq = new EquipamentosVisitaTecnica();
                    eq.setEquipamento(equipamentoService.buscarPorCodigo(eqDto.getCodigoEquipamento())
                            .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado com o código: " + eqDto.getCodigoEquipamento())));
                    eq.setQtde(eqDto.getQtde());
                    eq.setVisitaTecnica(entity); // Define a visita técnica
                    return eq;
                })
                .collect(Collectors.toList()));
        }

        // Converte PecaReposicaoVisitaTecnicaDTO para PecaReposicaoVisitaTecnica
        if (request.getPecasReposicaoVisitaTecnica() != null) {
            entity.setPecasReposicaoVisitaTecnica(request.getPecasReposicaoVisitaTecnica().stream()
                .map(pecaDto -> {
                    PecasReposicaoVisitaTecnica peca = new PecasReposicaoVisitaTecnica();
                    peca.setPecaReposicao(pecaReposicaoService.buscarPorCodigo(pecaDto.getCodigoPecaReposicao())
                            .orElseThrow(() -> new IllegalArgumentException("Peça de Reposição não encontrada com o código: " + pecaDto.getCodigoPecaReposicao())));
                    peca.setQtde(pecaDto.getQtde());
                    peca.setVisitaTecnica(entity); // Define a visita técnica
                    return peca;
                })
                .collect(Collectors.toList()));
        }
        
        return entity;
    }
}