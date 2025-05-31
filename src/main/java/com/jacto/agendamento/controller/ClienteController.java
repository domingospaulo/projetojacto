package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.ClienteDTO;
import com.jacto.agendamento.controller.dto.PessoaDTO;
import com.jacto.agendamento.controller.requests.ClienteRequest;
import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.service.ClienteService;
import com.jacto.agendamento.service.PessoaService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<ClienteDTO> buscarPorMatricula(@PathVariable Long matricula) {
        Optional<Cliente> cliente = clienteService.buscarPorMatricula(matricula);
        return cliente.map(c -> ResponseEntity.ok(convertToDto(c)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ClienteDTO salvar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = convertToEntity(request);
        Cliente salvo = clienteService.salvar(cliente);
        return convertToDto(salvo);
    }

    @PutMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long matricula, @Valid @RequestBody ClienteRequest request) {
        return clienteService.buscarPorMatricula(matricula)
                .map(c -> {
                    Cliente cliente = convertToEntity(request);
                    return ResponseEntity.ok(convertToDto(clienteService.salvar(cliente)));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
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
           PessoaDTO pessoaDTO = new PessoaDTO();
           pessoaDTO.setCpfCnpj(cliente.getPessoa().getCpfCnpj());
           pessoaDTO.setNome(cliente.getPessoa().getNome());
           pessoaDTO.setEmail(cliente.getPessoa().getEmail());
           pessoaDTO.setTelefone(cliente.getPessoa().getTelefone());
           dto.setPessoa(pessoaDTO);
       }

       return dto;
   }

    private Cliente convertToEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setMatricula(request.getMatricula());

        // Busca a pessoa pelo CPF usando o serviço
        Optional<Pessoa> pessoaOpt = pessoaService.buscarPorCpfCnpj(request.getCpfCnpj());
        Pessoa pessoa;
        if (pessoaOpt.isPresent()) {
            pessoa = pessoaOpt.get();
        } else {
            pessoa = new Pessoa();
            pessoa.setCpfCnpj(request.getCpfCnpj());
        }

        pessoa.setEmail(request.getEmail());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setNome(request.getNome());
        cliente.setPessoa(pessoa);

        return cliente;
    }
}