package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.OcorrenciaDTO;
import com.jacto.agendamento.entity.Ocorrencia;
import com.jacto.agendamento.service.OcorrenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService service;

    @GetMapping
    public List<OcorrenciaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<OcorrenciaDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Ocorrencia> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OcorrenciaDTO> salvar(@Valid @RequestBody OcorrenciaDTO dto) {
        Ocorrencia entity = convertToEntity(dto);
        Ocorrencia salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<OcorrenciaDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody OcorrenciaDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(dto.getDescricao());
                Ocorrencia feitoAtualizar = service.salvar(c);
                return ResponseEntity.ok(convertToDto(feitoAtualizar));
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
    private OcorrenciaDTO convertToDto(Ocorrencia entity) {
        return new OcorrenciaDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converter DTO para entidade
    private Ocorrencia convertToEntity(OcorrenciaDTO dto) {
        Ocorrencia entity = new Ocorrencia();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}