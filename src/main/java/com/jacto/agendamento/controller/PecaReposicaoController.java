package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.PecaReposicaoDTO;
import com.jacto.agendamento.entity.PecaReposicao;
import com.jacto.agendamento.service.PecaReposicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/pecas-reposicao")
public class PecaReposicaoController {

    @Autowired
    private PecaReposicaoService service;

    @GetMapping
    public List<PecaReposicaoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PecaReposicaoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<PecaReposicao> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PecaReposicaoDTO> salvar(@Valid @RequestBody PecaReposicaoDTO dto) {
        PecaReposicao entity = convertToEntity(dto);
        PecaReposicao salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PecaReposicaoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody PecaReposicaoDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(dto.getDescricao());
                PecaReposicao atualizado = service.salvar(c);
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
    private PecaReposicaoDTO convertToDto(PecaReposicao entity) {
        return new PecaReposicaoDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converter DTO para entidade
    private PecaReposicao convertToEntity(PecaReposicaoDTO dto) {
        PecaReposicao entity = new PecaReposicao();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}