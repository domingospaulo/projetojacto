package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.OperacaoDTO;
import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.service.OperacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<OperacaoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<OperacaoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Operacao> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OperacaoDTO> salvar(@Valid @RequestBody OperacaoDTO dto) {
        Operacao entity = convertToEntity(dto);
        Operacao salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<OperacaoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody OperacaoDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(dto.getDescricao());
                Operacao atualizado = service.salvar(c);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
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
    private Operacao convertToEntity(OperacaoDTO dto) {
        Operacao entity = new Operacao();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}