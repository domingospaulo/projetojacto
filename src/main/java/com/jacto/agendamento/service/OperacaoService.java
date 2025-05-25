package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Operacao;

import java.util.List;
import java.util.Optional;

public interface OperacaoService {
    List<Operacao> listarTodos();
    Optional<Operacao> buscarPorCodigo(Integer codigo);
    Operacao salvar(Operacao operacao);
    void deletar(Integer codigo);
}