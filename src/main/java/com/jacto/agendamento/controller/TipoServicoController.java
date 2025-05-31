package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.TipoServicoDTO;
import com.jacto.agendamento.entity.TipoServico;
import com.jacto.agendamento.service.TipoServicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-servico")
public class TipoServicoController {

    @Autowired
    private TipoServicoService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<TipoServicoDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<TipoServicoDTO> buscarPorCodigo(@PathVariable Integer codigo) {
        Optional<TipoServico> opt = service.buscarPorCodigo(codigo);
        return opt.map(t -> ResponseEntity.ok(convertToDto(t))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<TipoServicoDTO> salvar(@Valid @RequestBody TipoServicoDTO dto) {
        TipoServico entity = convertToEntity(dto);
        TipoServico salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<TipoServicoDTO> atualizar(@PathVariable Integer codigo, @Valid @RequestBody TipoServicoDTO dto) {
        return service.buscarPorCodigo(codigo)
            .map(t -> {
                t.setDescricao(dto.getDescricao());
                TipoServico atualizado = service.salvar(t);
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

    private TipoServicoDTO convertToDto(TipoServico entity) {
        return new TipoServicoDTO(entity.getCodigo(), entity.getDescricao());
    }

    private TipoServico convertToEntity(TipoServicoDTO dto) {
        TipoServico entity = new TipoServico();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}