package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.PessoaDTO;
import com.jacto.agendamento.controller.requests.PessoaRequest;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<PessoaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{cpfCnpj}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PessoaDTO> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        Optional<Pessoa> opt = service.buscarPorCpfCnpj(cpfCnpj);
        return opt.map(p -> ResponseEntity.ok(convertToDto(p)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PessoaDTO> salvar(@Valid @RequestBody PessoaRequest request) {
        Pessoa entity = convertToEntity(request);
        Pessoa salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{cpfCnpj}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable String cpfCnpj, @Valid @RequestBody PessoaRequest request) {
        return service.buscarPorCpfCnpj(cpfCnpj)
            .map(p -> {
                p.setNome(request.getNome());
                p.setEmail(request.getEmail());
                p.setTelefone(request.getTelefone());
                Pessoa atualizado = service.salvar(p);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpfCnpj}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable String cpfCnpj) {
        if (service.buscarPorCpfCnpj(cpfCnpj).isPresent()) {
            service.deletar(cpfCnpj);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    private PessoaDTO convertToDto(Pessoa entity) {
        return new PessoaDTO(
            entity.getCpfCnpj(),
            entity.getNome(),
            entity.getEmail(),
            String.valueOf(entity.getTelefone())
        );
    }

    private Pessoa convertToEntity(PessoaRequest request) {
        Pessoa entity = new Pessoa();
        entity.setCpfCnpj(request.getCpfCnpj());
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone() != null ? request.getTelefone(): null);
        return entity;
    }

}