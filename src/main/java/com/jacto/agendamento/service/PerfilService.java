package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Perfil;

import java.util.List;
import java.util.Optional;

public interface PerfilService {
    List<Perfil> listarTodos();
    Optional<Perfil> buscarPorCodigo(Integer codigo);
    Perfil salvar(Perfil perfil);
    void deletar(Integer codigo);
}