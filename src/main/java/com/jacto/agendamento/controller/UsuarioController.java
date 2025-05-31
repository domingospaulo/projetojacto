package com.jacto.agendamento.controller;

import com.jacto.agendamento.controller.dto.UsuarioDTO;
import com.jacto.agendamento.controller.requests.UsuarioRequest;
import com.jacto.agendamento.entity.Usuario;
import com.jacto.agendamento.service.UsuarioService;
import com.jacto.agendamento.service.PerfilService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private PerfilService perfilService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ClienteService clienteService;

    // Listar todos
    @GetMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<UsuarioDTO> listarTodos() {
        return service.listarTodos()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Buscar por login
    @GetMapping("/{login}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<UsuarioDTO> buscarPorLogin(@PathVariable String login) {
        return service.buscarPorLogin(login)
            .map(this::convertToDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por perfil
    @GetMapping("/perfil/{codigoPerfil}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public List<UsuarioDTO> buscarPorPerfil(@PathVariable Integer codigoPerfil) {
        return service.buscarPorPerfilCodigo(codigoPerfil)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Criar novo usuário
    @PostMapping
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestBody UsuarioRequest request) {
         // Verifica se pelo menos um relacionamentos foi informado
        if (request.getMatriculaFuncionario() == null && request.getMatriculaCliente() == null) {
            throw new IllegalArgumentException("É obrigatório informar matricula do cliente ou do funcionário");
        }
       
        Usuario entity = convertToEntity(request);
        Usuario salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{login}")
    @PreAuthorize("hasAuthority('200') or hasAuthority('300')")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String login, @Valid @RequestBody UsuarioRequest request) {
        // Verifica se pelo menos um relacionamentos foi informado
        if (request.getMatriculaFuncionario() == null && request.getMatriculaCliente() == null) {
            throw new IllegalArgumentException("É obrigatório informar matricula do cliente ou do funcionário");
        }

        return service.buscarPorLogin(login)
            .map(existing -> {
                Usuario updated = convertToEntity(request);
                updated.setLogin(existing.getLogin()); // mantém o login
                Usuario salvo = service.salvar(updated);
                return ResponseEntity.ok(convertToDto(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{login}")    
    public ResponseEntity<Void> deletar(@PathVariable String login) {
        if (service.buscarPorLogin(login).isPresent()) {
            service.deletar(login);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    // Converte entidade para DTO
    private UsuarioDTO convertToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setLogin(usuario.getLogin());
        if (usuario.getPerfil() != null)
            dto.setCodigoPerfil(usuario.getPerfil().getCodigo());
        dto.setSenha(usuario.getSenha());
        dto.setDataHoraCadastro(usuario.getDataHoraCadastro());
        dto.setAtivo(usuario.getAtivo());
        if (usuario.getFuncionario() != null)
            dto.setMatriculaFuncionario(usuario.getFuncionario().getMatricula());
        if (usuario.getCliente() != null)
            dto.setMatriculaCliente(usuario.getCliente().getMatricula());
        return dto;
    }

    // Converter DTO para entidade
    private Usuario convertToEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();

        if (request.getLogin() != null)
            usuario.setLogin(request.getLogin());

        // Buscar perfil pelo código
        usuario.setPerfil(perfilService.buscarPorCodigo(request.getCodigoPerfil())
            .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado")));

        // Buscar ou criar Funcionario
        if (request.getMatriculaFuncionario() != null) {
            usuario.setFuncionario(
                funcionarioService.buscarPorMatricula(request.getMatriculaFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"))
            );
        }

        // Buscar ou criar Cliente
        if (request.getMatriculaCliente() != null) {
            usuario.setCliente(
                clienteService.buscarPorMatricula(request.getMatriculaCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"))
            );
        }

        usuario.setSenha(request.getSenha());
        usuario.setDataHoraCadastro(request.getDataHoraCadastro() != null ? request.getDataHoraCadastro() : new java.util.Date());
        usuario.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return usuario;
    }

}