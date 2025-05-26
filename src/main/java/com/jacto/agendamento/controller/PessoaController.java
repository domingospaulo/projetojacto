package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.PessoaDTO;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<PessoaDTO> listarTodos() {
        return service.listarTodos()
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{cpfCnpj}")
    public ResponseEntity<PessoaDTO> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        Optional<Pessoa> opt = service.buscarPorCpfCnpj(cpfCnpj);
        return opt.map(p -> ResponseEntity.ok(convertToDto(p)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@Valid @RequestBody PessoaDTO dto) {
        Pessoa entity = convertToEntity(dto);
        Pessoa salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{cpfCnpj}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable String cpfCnpj, @Valid @RequestBody PessoaDTO dto) {
        return service.buscarPorCpfCnpj(cpfCnpj)
            .map(p -> {
                p.setNome(dto.getNome());
                p.setEmail(dto.getEmail());
                p.setTelefone(Long.parseLong(dto.getTelefone()));
                Pessoa atualizado = service.salvar(p);
                return ResponseEntity.ok(convertToDto(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpfCnpj}")
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

    private Pessoa convertToEntity(PessoaDTO dto) {
        Pessoa entity = new Pessoa();
        entity.setCpfCnpj(dto.getCpfCnpj());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone() != null ? Long.parseLong(dto.getTelefone()) : null);
        return entity;
    }

}