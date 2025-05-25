package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.StatusVisita;

import java.util.List;
import java.util.Optional;

public interface StatusVisitaService {
    List<StatusVisita> listarTodos();
    Optional<StatusVisita> buscarPorCodigo(Integer codigo);
    StatusVisita salvar(StatusVisita statusVisita);
    void deletar(Integer codigo);
}
