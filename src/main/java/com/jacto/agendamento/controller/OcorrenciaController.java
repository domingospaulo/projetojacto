package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.OcorrenciaDTO;
import com.jacto.agendamento.controller.requests.OcorrenciaRequest;
import com.jacto.agendamento.entity.Ocorrencia;
import com.jacto.agendamento.service.OcorrenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<OcorrenciaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OcorrenciaDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<Ocorrencia> opt = service.buscarPorCodigo(codigo);
        return opt.map(c -> ResponseEntity.ok(convertToDto(c)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OcorrenciaDTO> salvar(@Valid @RequestBody OcorrenciaRequest request) {
        Ocorrencia entity = convertToEntity(request);
        Ocorrencia salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<OcorrenciaDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody OcorrenciaRequest request) {
        return service.buscarPorCodigo(codigo)
            .map(c -> {
                c.setDescricao(request.getDescricao());
                Ocorrencia feitoAtualizar = service.salvar(c);
                return ResponseEntity.ok(convertToDto(feitoAtualizar));
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
    private OcorrenciaDTO convertToDto(Ocorrencia entity) {
        return new OcorrenciaDTO(entity.getCodigo(), entity.getDescricao());
    }

    // Converter DTO para entidade
    private Ocorrencia convertToEntity(OcorrenciaRequest request) {
        Ocorrencia entity = new Ocorrencia();
        entity.setCodigo(request.getCodigo());
        entity.setDescricao(request.getDescricao());
        return entity;
    }

}