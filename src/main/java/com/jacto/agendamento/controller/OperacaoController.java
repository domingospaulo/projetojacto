package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.OperacaoDTO;
import com.jacto.agendamento.controller.requests.OperacaoRequest;
import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.service.OperacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/operacoes")
public class OperacaoController {

    @Autowired
    private OperacaoService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<OperacaoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OperacaoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Operacao> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OperacaoDTO> salvar(@Valid @RequestBody OperacaoRequest request) {
        Operacao entity = convertToEntity(request);
        Operacao salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OperacaoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody OperacaoRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(request.getDescricao());
                Operacao atualizado = service.salvar(c);
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

    // Converter entidade para DTO
    private OperacaoDTO convertToDto(Operacao entity) {
        return new OperacaoDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converter DTO para entidade
    private Operacao convertToEntity(OperacaoRequest request) {
        Operacao entity = new Operacao();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }

}