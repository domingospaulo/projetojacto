package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.PecaReposicao;

import java.util.List;
import java.util.Optional;

public interface PecaReposicaoService {
    List<PecaReposicao> listarTodos();
    Optional<PecaReposicao> buscarPorCodigo(Integer codigo);
    PecaReposicao salvar(PecaReposicao peca);
    void deletar(Integer codigo);
}
