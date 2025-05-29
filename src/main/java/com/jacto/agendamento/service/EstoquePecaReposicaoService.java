package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.EstoquePecaReposicao;

import java.util.List;
import java.util.Optional;

public interface EstoquePecaReposicaoService {
    List<EstoquePecaReposicao> listarTodos();
    Optional<EstoquePecaReposicao> buscarPorId(Long id);
    Optional<EstoquePecaReposicao> buscarPorCodigoPecaReposicao(Integer codigoPecaReposicao);
    EstoquePecaReposicao salvar(EstoquePecaReposicao estoque);
    void deletar(Long id);
}