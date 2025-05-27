package com.jacto.agendamento.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacto.agendamento.dto.EquipamentoVisitaTecnicaDTO;
import com.jacto.agendamento.dto.PecaReposicaoVisitaTecnicaDTO;
import com.jacto.agendamento.dto.VisitaTecnicaDTO;
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


        // Converte EquipamentosVisitaTecnica para EquipamentoVisitaTecnicaDTO
        if (entity.getEquipamentosVisitaTecnica() != null) {
            dto.setEquipamentosVisitaTecnica(entity.getEquipamentosVisitaTecnica().stream()
                .map(eq -> {
                    EquipamentoVisitaTecnicaDTO eqDto = new EquipamentoVisitaTecnicaDTO();
                    eqDto.setCodigoEquipamento(eq.getEquipamento().getCodigo());
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
                    pecaDto.setCodigoPecaReposicao(peca.getPecaReposicao().getCodigo());
                    pecaDto.setQtde(peca.getQtde());
                    return pecaDto;
                })
                .collect(Collectors.toList()));
        }

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

        // Converte EquipamentoVisitaTecnicaDTO para EquipamentoVisitaTecnica
        if (dto.getEquipamentosVisitaTecnica() != null) {
            entity.setEquipamentosVisitaTecnica(dto.getEquipamentosVisitaTecnica().stream()
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
        if (dto.getPecasReposicaoVisitaTecnica() != null) {
            entity.setPecasReposicaoVisitaTecnica(dto.getPecasReposicaoVisitaTecnica().stream()
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