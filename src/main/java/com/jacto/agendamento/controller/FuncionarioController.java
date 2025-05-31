package com.jacto.agendamento.controller;

import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.controller.dto.FuncionarioDTO;
import com.jacto.agendamento.entity.Cargo;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.PessoaService;
import com.jacto.agendamento.service.CargoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService service;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<FuncionarioDTO> listarTodos() {
        return service.listarTodos().stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<FuncionarioDTO> buscarPorMatricula(@PathVariable Long matricula) {
        Optional<Funcionario> opt = service.buscarPorMatricula(matricula);
        return opt.map(f -> ResponseEntity.ok(convertToDto(f)))
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cargo/{codigoCargo}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<FuncionarioDTO> buscarPorCodigoCargo(@PathVariable Integer codigoCargo) {
        return service.buscarPorCodigoCargo(codigoCargo).stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/pessoa/{cpfCnpj}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<FuncionarioDTO> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        Optional<Funcionario> funcionarioOpt = service.buscarPorCpfCnpj(cpfCnpj);
        return funcionarioOpt
                .map(f -> ResponseEntity.ok(convertToDto(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<FuncionarioDTO> salvar(@Valid @RequestBody FuncionarioDTO dto) {
        Funcionario entity = convertToEntity(dto);
        Funcionario salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long matricula, @Valid @RequestBody FuncionarioDTO dto) {
        return service.buscarPorMatricula(matricula)
            .map(f -> {
                Funcionario updated = convertToEntity(dto);
                updated.setMatricula(matricula);
                Funcionario salvo = service.salvar(updated);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<Void> deletar(@PathVariable Long matricula) {
        if (service.buscarPorMatricula(matricula).isPresent()) {
            service.deletar(matricula);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Converter entidade para DTO
    private FuncionarioDTO convertToDto(Funcionario entity) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setMatricula(entity.getMatricula());
        if (entity.getPessoa() != null) {
            dto.setCpfCnpj(entity.getPessoa().getCpfCnpj());
            dto.setNome(entity.getPessoa().getNome());
            dto.setEmail(entity.getPessoa().getEmail());
            dto.setTelefone(String.valueOf(entity.getPessoa().getTelefone()));
        }

        if (entity.getCargo() != null) {
            dto.setCodigoCargo(entity.getCargo().getCodigo());
        }
        return dto;
    }

    // Converter DTO para entidade, incluindo busca de cargo
    private Funcionario convertToEntity(FuncionarioDTO dto) {
        Funcionario entity = new Funcionario();

        if (dto.getMatricula() != null) {
            entity.setMatricula(dto.getMatricula());
        }

        // Buscar cargo pelo código enviado
        Optional<Cargo> cargoOpt = cargoService.buscarPorCodigo(dto.getCodigoCargo());
        if (!cargoOpt.isPresent()) {
            throw new IllegalArgumentException("Cargo não encontrado com código: " + dto.getCodigoCargo());
        }
        entity.setCargo(cargoOpt.get());

        // Busca a pessoa pelo CPF usando o serviço
        Optional<Pessoa> pessoaOpt = pessoaService.buscarPorCpfCnpj(dto.getCpfCnpj());
        Pessoa pessoa;
        if (pessoaOpt.isPresent()) {
            pessoa = pessoaOpt.get();
        } else {
            pessoa = new Pessoa();
            pessoa.setCpfCnpj(dto.getCpfCnpj());
        }

        pessoa.setEmail(dto.getEmail());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setNome(dto.getNome());
        entity.setPessoa(pessoa);

        return entity;
    }

}