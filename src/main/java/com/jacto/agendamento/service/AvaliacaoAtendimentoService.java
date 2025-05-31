package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.AvaliacaoAtendimento;

import java.util.List;
import java.util.Optional;
import java.util.Date;

public interface AvaliacaoAtendimentoService {

    List<AvaliacaoAtendimento> listarTodos();

    Optional<AvaliacaoAtendimento> buscarPorId(Long id);

    AvaliacaoAtendimento salvar(AvaliacaoAtendimento avaliacao);

    void deletar(Long id);

    // Buscar por visita técnica
    List<AvaliacaoAtendimento> buscarPorVisita(Long idVisitaTecnica);

    // Buscar média das avaliações por funcionário
    Double mediaAvaliacoesPorFuncionario(Long matriculaFuncionario);

    // Buscar avaliações pelo matrícula do funcionário
    List<AvaliacaoAtendimento> buscarPorMatriculaFuncionario(Long matriculaFuncionario);

    // Buscar avaliações entre duas datas de operação
    List<AvaliacaoAtendimento> buscarPorPeriodoDataOperacao(Date inicio, Date fim);

    // Buscar avaliações pelo matrícula do cliente associado à visita técnica
    List<AvaliacaoAtendimento> buscarPorMatriculaCliente(Long matricula);
}