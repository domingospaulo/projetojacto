package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorLogin(String login);
    Optional<Usuario> buscarPorPerfilCodigo(Integer perfilCodigo);
    Usuario salvar(Usuario usuario);
    void deletar(String login);

    boolean validarCredenciais(String login, String senha);
    String buscarPerfilPorLogin(String login);
}