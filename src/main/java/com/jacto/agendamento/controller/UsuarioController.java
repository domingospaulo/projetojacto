package com.jacto.agendamento.controller;

import com.jacto.agendamento.dto.UsuarioDTO;
import com.jacto.agendamento.entity.Usuario;
import com.jacto.agendamento.service.UsuarioService;
import com.jacto.agendamento.service.PerfilService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public List<UsuarioDTO> listarTodos() {
        return service.listarTodos()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Buscar por login
    @GetMapping("/{login}")
    public ResponseEntity<UsuarioDTO> buscarPorLogin(@PathVariable String login) {
        return service.buscarPorLogin(login)
            .map(this::convertToDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por perfil
    @GetMapping("/perfil/{codigoPerfil}")
    public List<UsuarioDTO> buscarPorPerfil(@PathVariable Integer codigoPerfil) {
        return service.buscarPorPerfilCodigo(codigoPerfil)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Criar novo usuário
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
         // Verifica se pelo menos um relacionamentos foi informado
        if (dto.getMatriculaFuncionario() == null && dto.getMatriculaCliente() == null) {
            throw new IllegalArgumentException("É obrigatório informar matricula do cliente ou do funcionário");
        }
       
        Usuario entity = convertToEntity(dto);
        Usuario salvo = service.salvar(entity);
        return ResponseEntity.ok(convertToDto(salvo));
    }

    @PutMapping("/{login}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String login, @Valid @RequestBody UsuarioDTO dto) {
        // Verifica se pelo menos um relacionamentos foi informado
        if (dto.getMatriculaFuncionario() == null && dto.getMatriculaCliente() == null) {
            throw new IllegalArgumentException("É obrigatório informar matricula do cliente ou do funcionário");
        }

        return service.buscarPorLogin(login)
            .map(existing -> {
                Usuario updated = convertToEntity(dto);
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
    private Usuario convertToEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        if (dto.getLogin() != null)
            usuario.setLogin(dto.getLogin());

        // Buscar perfil pelo código
        usuario.setPerfil(perfilService.buscarPorCodigo(dto.getCodigoPerfil())
            .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado")));

        // Buscar ou criar Funcionario
        if (dto.getMatriculaFuncionario() != null) {
            usuario.setFuncionario(
                funcionarioService.buscarPorMatricula(dto.getMatriculaFuncionario())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"))
            );
        }

        // Buscar ou criar Cliente
        if (dto.getMatriculaCliente() != null) {
            usuario.setCliente(
                clienteService.buscarPorMatricula(dto.getMatriculaCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"))
            );
        }

        usuario.setSenha(dto.getSenha());
        usuario.setDataHoraCadastro(dto.getDataHoraCadastro() != null ? dto.getDataHoraCadastro() : new java.util.Date());
        usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        return usuario;
    }

}