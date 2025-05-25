package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Buscar usuário pelo login
    List<Usuario> findByLogin(String login);

    // Buscar todos os usuários por código do perfil
    Optional<Usuario> findByPerfilCodigo(Integer perfilCodigo);
}