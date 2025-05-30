package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Usuario;
import com.jacto.agendamento.repository.UsuarioRepository;
import com.jacto.agendamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return repository.findById(login);
    }

    @Override
    public Optional<Usuario> buscarPorPerfilCodigo(Integer perfilCodigo) {
        return repository.findByPerfilCodigo(perfilCodigo);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void deletar(String login) {
        repository.deleteById(login);
    }

    public boolean validarCredenciais(String login, String senha) {
        Optional<Usuario> usuarioOpt = repository.findById(login);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return passwordEncoder.matches(senha, usuario.getSenha());
        }
        return false;
    }

    public String buscarPerfilPorLogin(String login) {
        Optional<Usuario> usuarioOpt = repository.findById(login);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return String.valueOf(usuario.getPerfil().getCodigo());
        }
        return null;
    }
}    