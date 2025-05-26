package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.FazendaDTO;
import com.jacto.agendamento.entity.Fazenda;
import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.service.FazendaService;
import com.jacto.agendamento.service.LocalizacaoService;
import com.jacto.agendamento.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/fazendas")
public class FazendaController {

    @Autowired
    private FazendaService service;
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @GetMapping
    public List<FazendaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FazendaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Fazenda> optional = service.buscarPorId(id);
        return optional.map(this::convertToDto)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula-cliente/{matriculaCliente}")
    public ResponseEntity<List<FazendaDTO>> buscarPorMatriculaCliente(@PathVariable Long matriculaCliente) {
        List<Fazenda> fazendas = service.buscarPorMatriculaCliente(matriculaCliente);
        if (fazendas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma fazenda encontrada para matrícula do cliente: " + matriculaCliente);
        }
        List<FazendaDTO> dtos = fazendas.stream()
                                    .map(this::convertToDto)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<FazendaDTO> salvar(@Valid @RequestBody FazendaDTO dto) {
        Fazenda entity = convertToEntity(dto);
        Fazenda salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FazendaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FazendaDTO dto) {
        return service.buscarPorId(id)
            .map(existing -> {
                Fazenda atualizado = convertToEntity(dto);
                atualizado.setId(existing.getId()); // mantém o id
                Fazenda salvo = service.salvar(atualizado);
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
    private FazendaDTO convertToDto(Fazenda entity) {
        FazendaDTO dto = new FazendaDTO();
        dto.setId(entity.getId());
        // Obter matrícula do cliente pelo relacionamento
        if (entity.getCliente() != null) {
            dto.setMatriculaCliente(entity.getCliente().getMatricula());
        }

        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());

        //Carregando detalhes da localização
        dto.setDescricao(localizacaoService.getDetalhesLocalizacao(entity.getLatitude(), entity.getLongitude()));
        dto.setEndereco(entity.getEndereco());
        dto.setDataHoraCadastro(entity.getDataHoraCadastro());
        dto.setAtivo(entity.getAtivo());
        return dto;
    }

    // Converter DTO para entidade, incluindo busca do cliente
    private Fazenda convertToEntity(FazendaDTO dto) {
        Fazenda entity = new Fazenda();

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        // Buscar cliente pelo ID de matrícula
        Cliente cliente = clienteService.buscarPorMatricula(dto.getMatriculaCliente())
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para matrícula: " + dto.getMatriculaCliente()));

        entity.setCliente(cliente);
        entity.setDescricao(dto.getDescricao());
        entity.setEndereco(dto.getEndereco());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setDataHoraCadastro(dto.getDataHoraCadastro() != null ? dto.getDataHoraCadastro() : new java.util.Date());
        entity.setAtivo(dto.getAtivo());

        return entity;
    }

}