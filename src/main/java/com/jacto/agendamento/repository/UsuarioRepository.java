package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Buscar usuário pelo login
    Optional<Usuario> findByLogin(String login);

    // Buscar todos os usuários por código do perfil
    List<Usuario> findByPerfilCodigo(Integer perfilCodigo);
}