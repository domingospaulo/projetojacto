package com.jacto.agendamento.controller;

import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.dto.ClienteDTO;
import com.jacto.agendamento.service.ClienteService;
import com.jacto.agendamento.service.PessoaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<ClienteDTO> buscarPorMatricula(@PathVariable Long matricula) {
        Optional<Cliente> cliente = clienteService.buscarPorMatricula(matricula);
        return cliente.map(c -> ResponseEntity.ok(convertToDto(c)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClienteDTO salvar(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente salvo = clienteService.salvar(cliente);
        return convertToDto(salvo);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long matricula, @Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.buscarPorMatricula(matricula)
                .map(c -> {
                    Cliente cliente = convertToEntity(clienteDTO);
                    return ResponseEntity.ok(convertToDto(clienteService.salvar(cliente)));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable Long matricula) {
        if (clienteService.buscarPorMatricula(matricula).isPresent()) {
            clienteService.deletar(matricula);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    private ClienteDTO convertToDto(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setMatricula(cliente.getMatricula());
        if (cliente.getPessoa() != null) {
            dto.setCpfCnpj(cliente.getPessoa().getCpfCnpj());
            dto.setNome(cliente.getPessoa().getNome());
            dto.setEmail(cliente.getPessoa().getEmail());
            dto.setTelefone(String.valueOf(cliente.getPessoa().getTelefone()));
        }
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setMatricula(dto.getMatricula());

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
        pessoa.setTelefone(Long.parseLong(dto.getTelefone()));
        pessoa.setNome(dto.getNome());
        cliente.setPessoa(pessoa);

        return cliente;
    }
}