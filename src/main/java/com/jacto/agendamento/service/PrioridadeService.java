package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Prioridade;

import java.util.List;
import java.util.Optional;

public interface PrioridadeService {
    List<Prioridade> listarTodos();
    Optional<Prioridade> buscarPorCodigo(Integer codigo);
    Prioridade salvar(Prioridade prioridade);
    void deletar(Integer codigo);
}